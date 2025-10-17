package com.example.decathlon.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.example.decathlon.common.ValidationUtil;
import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;
import com.example.decathlon.excel.ExcelPrinter;

public class MainGUI {

    private JTextField nameField;
    private JTextField resultField;
    private JComboBox<String> disciplineBox;
    private JTextArea outputArea;
    private JComboBox<String> disciplineCategoryBox;
    private Map<String, Competitor> competitors = new HashMap<>();

    private final String[] decathlonDisciplines = {
            "Deca_100m", "Deca_400m", "Deca_1500m", "Deca_110m Hurdles",
            "Deca_Long Jump", "Deca_High Jump", "Deca_Pole Vault",
            "Deca_Discus Throw", "Deca_Javelin Throw", "Deca_Shot Put"
    };

    private final String[] heptathlonDisciplines = {
            "Hep_100mHurdles", "Hep_200m", "Hep_800m", "Hep_HightJump",
            "Hep_JavelinThrow", "Hep_LongJump", "Hep_ShotPut"
    };

    public static void main(String[] args) {
        new MainGUI().createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Track and Field Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel(new GridLayout(9, 1));

        nameField = new JTextField(20);
        panel.add(new JLabel("Enter Competitor's Name:"));
        panel.add(nameField);

        String[] categories = {"", "Decathlon", "Heptathlon"};
        disciplineCategoryBox = new JComboBox<>(categories);
        panel.add(new JLabel("Select Discipline:"));
        panel.add(disciplineCategoryBox);

        disciplineBox = new JComboBox<>();
        panel.add(new JLabel("Select Event:"));
        panel.add(disciplineBox);

        disciplineCategoryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) disciplineCategoryBox.getSelectedItem();
                disciplineBox.removeAllItems();
                disciplineBox.addItem("");
                if ("Decathlon".equals(selectedCategory)) {
                    for (String d : decathlonDisciplines) {
                        disciplineBox.addItem(d);
                    }
                } else if ("Heptathlon".equals(selectedCategory)) {
                    for (String d : heptathlonDisciplines) {
                        disciplineBox.addItem(d);
                    }
                }
            }
        });

        resultField = new JTextField(10);
        panel.add(new JLabel("Enter Result:"));
        panel.add(resultField);

        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());
        panel.add(calculateButton);

        JButton exportButton = new JButton("Export to Excel");
        exportButton.addActionListener(ae -> {
            JFrame f = (JFrame) SwingUtilities.getWindowAncestor((Component) ae.getSource());
            if (competitors.isEmpty()) {
                JOptionPane.showMessageDialog(f, "No data to export.");
                return;
            }
            try {
                List<String> orderedEvents = new ArrayList<>();
                for (String d : decathlonDisciplines) orderedEvents.add(d);
                for (String d : heptathlonDisciplines) orderedEvents.add(d);

                List<String> usedEvents = new ArrayList<>();
                for (String ev : orderedEvents) {
                    boolean present = false;
                    for (Competitor c : competitors.values()) {
                        if (c.getResults().containsKey(ev)) { present = true; break; }
                    }
                    if (present) usedEvents.add(ev);
                }

                List<Competitor> sorted = new ArrayList<>(competitors.values());
                sorted.sort((c1, c2) -> Integer.compare(c2.getTotalScore(), c1.getTotalScore()));

                List<Object[]> rows = new ArrayList<>();
                List<Object> header = new ArrayList<>();
                header.add("Rank");
                header.add("Name");
                header.addAll(usedEvents);
                header.add("Total Score");
                rows.add(header.toArray(new Object[0]));

                int rank = 1;
                for (Competitor c : sorted) {
                    List<Object> r = new ArrayList<>();
                    r.add(rank);
                    r.add(c.getName());
                    for (String ev : usedEvents) {
                        Competitor.DisciplineResult dr = c.getResults().get(ev);
                        r.add(dr == null ? "" : dr.score);
                    }
                    r.add(c.getTotalScore());
                    rows.add(r.toArray(new Object[0]));
                    rank++;
                }

                ExcelPrinter printer = new ExcelPrinter("standings");
                printer.add(rows.toArray(new Object[0][]), "Standings");
                printer.write();
                JOptionPane.showMessageDialog(f, "Exported to Excel.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Export failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(exportButton);

        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static String toTitleCase(String s) {
        String[] parts = s.trim().toLowerCase().split("\\s+");
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) continue;
            b.append(Character.toUpperCase(parts[i].charAt(0)));
            if (parts[i].length() > 1) b.append(parts[i].substring(1));
            if (i < parts.length - 1) b.append(" ");
        }
        return b.toString();
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String rawName = nameField.getText();
            if (rawName == null) rawName = "";
            if (!rawName.trim().matches("^[A-Za-z ]+$")) {
                JOptionPane.showMessageDialog(null,
                        "The Name field can contain only letters and spaces",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = toTitleCase(rawName);

            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText();
            String normalized = resultText == null ? "" : resultText.replace(',', '.');

            try {
                double result = Double.parseDouble(normalized);
                int score = 0;

                switch (discipline) {
                    case "Deca_100m":
                        ValidationUtil.validateResult("Deca 100m", result, 5, 20);
                        score = new Deca100M().calculateResult(result);
                        break;
                    case "Deca_400m":
                        ValidationUtil.validateResult("Deca 400m", result, 20, 100);
                        score = new Deca400M().calculateResult(result);
                        break;
                    case "Deca_1500m":
                        ValidationUtil.validateResult("Deca 1500m", result, 150, 400);
                        score = new Deca1500M().calculateResult(result);
                        break;
                    case "Deca_110m Hurdles":
                        ValidationUtil.validateResult("Deca 110m Hurdles", result, 10, 30);
                        score = new Deca110MHurdles().calculateResult(result);
                        break;
                    case "Deca_Long Jump":
                        ValidationUtil.validateResult("Deca Long Jump", result, 0, 1000);
                        score = new DecaLongJump().calculateResult(result);
                        break;
                    case "Deca_High Jump":
                        ValidationUtil.validateResult("Deca High Jump", result, 0, 300);
                        score = new DecaHighJump().calculateResult(result);
                        break;
                    case "Deca_Pole Vault":
                        ValidationUtil.validateResult("Deca Pole Vault", result, 0, 1000);
                        score = new DecaPoleVault().calculateResult(result);
                        break;
                    case "Deca_Discus Throw":
                        ValidationUtil.validateResult("Deca Discus Throw", result, 0, 85);
                        score = new DecaDiscusThrow().calculateResult(result);
                        break;
                    case "Deca_Javelin Throw":
                        ValidationUtil.validateResult("Deca Javelin Throw", result, 0, 110);
                        score = new DecaJavelinThrow().calculateResult(result);
                        break;
                    case "Deca_Shot Put":
                        ValidationUtil.validateResult("Deca Shot Put", result, 0, 30);
                        score = new DecaShotPut().calculateResult(result);
                        break;
                    case "Hep_100mHurdles":
                        ValidationUtil.validateResult("Hep 100m Hurdles", result, 10, 30);
                        score = new Hep100MHurdles().calculateResult(result);
                        break;
                    case "Hep_200m":
                        ValidationUtil.validateResult("Hep 200m", result, 20, 100);
                        score = new Hep200M().calculateResult(result);
                        break;
                    case "Hep_800m":
                        ValidationUtil.validateResult("Hep 800m", result, 70, 250);
                        score = new Hep800M().calculateResult(result);
                        break;
                    case "Hep_HightJump":
                        ValidationUtil.validateResult("Hep High Jump", result, 0, 300);
                        score = new HeptHightJump().calculateResult(result);
                        break;
                    case "Hep_JavelinThrow":
                        ValidationUtil.validateResult("Hep Javelin Throw", result, 0, 110);
                        score = new HeptJavelinThrow().calculateResult(result);
                        break;
                    case "Hep_LongJump":
                        ValidationUtil.validateResult("Hep Long Jump", result, 0, 1000);
                        score = new HeptLongJump().calculateResult(result);
                        break;
                    case "Hep_ShotPut":
                        ValidationUtil.validateResult("Hep Shot Put", result, 0, 30);
                        score = new HeptShotPut().calculateResult(result);
                        break;
                }

                Competitor competitor = competitors.get(name);
                if (competitor == null) {
                    competitor = new Competitor(name);
                }
                competitor.addScore(discipline, result, score);
                competitors.put(name, competitor);

                List<Competitor> sortedCompetitors = new ArrayList<>(competitors.values());
                sortedCompetitors.sort((c1, c2) -> Integer.compare(c2.getTotalScore(), c1.getTotalScore()));

                StringBuilder output = new StringBuilder();
                int rank = 1;
                for (Competitor c : sortedCompetitors) {
                    output.append(rank).append(". ").append(c.getName())
                            .append(" - Total Score: ").append(c.getTotalScore()).append("\n");
                    for (Map.Entry<String, Competitor.DisciplineResult> entry : c.getResults().entrySet()) {
                        Competitor.DisciplineResult dr = entry.getValue();
                        output.append("   ").append(entry.getKey()).append(": ").append(dr.score).append("\n");
                    }
                    output.append("\n");
                    rank++;
                }

                outputArea.setText(output.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid number for the result.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
