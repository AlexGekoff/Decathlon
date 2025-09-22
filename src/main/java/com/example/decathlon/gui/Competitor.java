package com.example.decathlon.gui;
import java.util.HashMap;
import java.util.Map;

public class Competitor {
    private String name;
    //private Map<String, Integer> scores; // discipline -> score
    private int totalScore;
    private Map<String, DisciplineResult> results; // discipline -> DisciplineResult

    // Inner class to store the result and score for each discipline
    public static class DisciplineResult {
        double result; // the actual value user entered
        int score;     // calculated score

        public DisciplineResult(double result, int score) {
            this.result = result;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Result = " + result + ", Score = " + score;
        }
    }


    public Competitor(String name) {
        this.name = name;
        this.results = new HashMap<>();
        this.totalScore = 0;
    }

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addScore(String discipline, double result, int score) {
        // If the event was already added, subtract old score
        if (results.containsKey(discipline)) {
            totalScore -= results.get(discipline).score;
        }
        results.put(discipline, new DisciplineResult(result, score));
        totalScore += score;
    }

    public Map<String, DisciplineResult> getResults() {
        return results;
    }


}
