package com.example.decathlon.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText();

            try {
                double result = Double.parseDouble(resultText);

                int score = 0;
                switch (discipline) {
                    case "Deca_100m":
                        Deca100M deca100M = new Deca100M();
                        score = deca100M.calculateResult(result);
                        break;
                    case "Deca_400m":
                        Deca400M deca400M = new Deca400M();
                        score = deca400M.calculateResult(result);
                        break;
                    case "Deca_1500m":
                        Deca1500M deca1500M = new Deca1500M();
                        score = deca1500M.calculateResult(result);
                        break;
                    case "Deca_110m Hurdles":
                        Deca110MHurdles deca110MHurdles = new Deca110MHurdles();
                        score = deca110MHurdles.calculateResult(result);
                        break;
                    case "Deca_Long Jump":
                        DecaLongJump decaLongJump = new DecaLongJump();
                        score = decaLongJump.calculateResult(result);
                        break;
                    case "Deca_High Jump":
                        DecaHighJump decaHighJump = new DecaHighJump();
                        score = decaHighJump.calculateResult(result);
                        break;
                    case "Deca_Pole Vault":
                        DecaPoleVault decaPoleVault = new DecaPoleVault();
                        score = decaPoleVault.calculateResult(result);
                        break;
                    case "Deca_Discus Throw":
                        DecaDiscusThrow decaDiscusThrow = new DecaDiscusThrow();
                        score = decaDiscusThrow.calculateResult(result);
                        break;
                    case "Deca_Javelin Throw":
                        DecaJavelinThrow decaJavelinThrow = new DecaJavelinThrow();
                        score = decaJavelinThrow.calculateResult(result);
                        break;
                    case "Deca_Shot Put":
                        DecaShotPut decaShotPut = new DecaShotPut();
                        score = decaShotPut.calculateResult(result);
                        break;
                    case "Hep_100mHurdles":
                        Hep100MHurdles hep100MHurdles = new Hep100MHurdles();
                        score = hep100MHurdles.calculateResult(result);
                        break;
                    case "Hep_200m":
                        Hep200M hep200M=new Hep200M();
                        score = hep200M.calculateResult(result);
                        break;
                    case "Hep_800m":
                        Hep800M hep800M=new Hep800M();
                        score = hep800M.calculateResult(result);
                        break;
                    case "Hep_HightJump":
                        HeptHightJump heptHightJump=new HeptHightJump();
                        score = heptHightJump.calculateResult(result);
                        break;
                    case "Hep_JavelinThrow":
                        HeptJavelinThrow heptJavelinThrow=new HeptJavelinThrow();
                        score = heptJavelinThrow.calculateResult(result);
                        break;
                    case "Hep_LongJump":
                        HeptLongJump heptLongJump=new HeptLongJump();
                        score = heptLongJump.calculateResult(result);
                        break;
                    case "Hep_ShotPut":
                        HeptShotPut heptShotPut=new HeptShotPut();
                        score = heptShotPut.calculateResult(result);
                        break;


                }
                // Update or add competitor to the map

                Competitor competitor = competitors.getOrDefault(name, new Competitor(name));
                competitor.addScore(discipline, result, score);
                competitors.put(name, competitor);

                // Sort all competitors by total score in descending order
                List<Competitor> sortedCompetitors = new ArrayList<>(competitors.values());
                sortedCompetitors.sort((c1, c2) -> Integer.compare(c2.getTotalScore(), c1.getTotalScore()));

                // Build the output string showing ranking, total score, and scores per event
                StringBuilder output = new StringBuilder();
                int rank = 1;
                for (Competitor c : sortedCompetitors) {
                    output.append(rank).append(". ").append(c.getName())
                            .append(" - Total Score: ").append(c.getTotalScore()).append("\n");
                    for (Map.Entry<String,Competitor.DisciplineResult> entry : c.getResults().entrySet()) {
                        output.append("   ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                    }
                    output.append("\n");
                    rank++;
                }

                // Display the results in the output area
                outputArea.setText(output.toString());

            } catch (NumberFormatException ex) {
                // Show error message if result input is not a valid number
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the result.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
