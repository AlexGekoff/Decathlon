package com.example.decathlon.model;

import java.util.HashMap;
import java.util.Map;

public class WebCompetitor {
    private String name;
    private Map<String, Double> scores = new HashMap<>();
    private double total;

    public WebCompetitor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Map<String, Double> getScores() {
        return scores;
    }
    public double getTotal() {
        return total;
    }

    public void addScore(String event, double raw) {

        scores.put(event, raw);
        recalcTotal();
    }

    private void recalcTotal() {
        total = scores.values().stream().mapToDouble(Double::doubleValue).sum();
    }

}
