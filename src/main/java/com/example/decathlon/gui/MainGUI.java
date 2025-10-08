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
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new GridLayout(7, 1)); // 7 rader för alla komponenter

        // Input för tävlandes namn
        nameField = new JTextField(20);
        panel.add(new JLabel("Enter Competitor's Name:"));
        panel.add(nameField);

        // Dropdown för att välja disciplin-kategori (tom som start)
        String[] categories = {"", "Decathlon", "Heptathlon"}; // tom sträng först
        disciplineCategoryBox = new JComboBox<>(categories);
        panel.add(new JLabel("Select Discipline:"));
        panel.add(disciplineCategoryBox);

        // Dropdown för grenarna (disciplineBox) – börjar tom
        disciplineBox = new JComboBox<>();
        panel.add(new JLabel("Select Event:"));
        panel.add(disciplineBox);

        // När kategori ändras -> uppdatera grenlistan
        disciplineCategoryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) disciplineCategoryBox.getSelectedItem();
                disciplineBox.removeAllItems(); //// töm listan

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

        // Input för resultat
        resultField = new JTextField(10);
        panel.add(new JLabel("Enter Result:"));
        panel.add(resultField);

        // Knapp för att räkna ut resultat
        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());
        panel.add(calculateButton);

        // Output area
        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);
    }



    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();

            if (!name.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(null,
                        "The Name field can contain only letters",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText();

            try {
                double result = Double.parseDouble(resultText);

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
                        ValidationUtil.validateResult("Hep High Jump", result, 0,300);
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

                // Update or add competitor
                Competitor competitor = competitors.getOrDefault(name, new Competitor(name));
                competitor.addScore(discipline, result, score);
                competitors.put(name, competitor);

                // Sort competitors
                List<Competitor> sortedCompetitors = new ArrayList<>(competitors.values());
                sortedCompetitors.sort((c1, c2) -> Integer.compare(c2.getTotalScore(), c1.getTotalScore()));

                // Build output
                StringBuilder output = new StringBuilder();
                int rank = 1;
                for (Competitor c : sortedCompetitors) {
                    output.append(rank).append(". ").append(c.getName())
                            .append(" - Total Score: ").append(c.getTotalScore()).append("\n");
                    for (Map.Entry<String, Competitor.DisciplineResult> entry : c.getResults().entrySet()) {
                        output.append("   ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
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
    }}
