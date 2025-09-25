package com.example.decathlon.service;

import com.example.decathlon.deca.*;
import com.example.decathlon.model.WebCompetitor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecathlonService {

    private final List<WebCompetitor> competitors = new ArrayList<>();

    public WebCompetitor addCompetitor(String name) {
        WebCompetitor competitor = new WebCompetitor(name);
        competitors.add(competitor);
        return competitor;
    }

    public WebCompetitor findCompetitorByName(String name) {
        return competitors.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Competitor not found"));
    }

    public WebCompetitor addScore(String name, String event, double raw) {
        WebCompetitor competitor = findCompetitorByName(name);
        int score = 0;

        switch(event) {
            case "100m":
                Deca100M deca100M = new Deca100M();
                score = deca100M.calculateResult(raw);
                break;
            case "longJump":
                DecaLongJump decaLongJump = new DecaLongJump();
                score = decaLongJump.calculateResult(raw);
                break;
            case "shotPut":
                DecaShotPut decaShotPut = new DecaShotPut();
                score = decaShotPut.calculateResult(raw);
                break;
            case "400m":
                Deca400M deca400M = new Deca400M();
                score = deca400M.calculateResult(raw);
                break;


            // Lägg till fler events här
        }

        competitor.addScore(event, score); // nu sparas poängen, inte raw
        return competitor;
    }

    public List<WebCompetitor> getStandings() {
        competitors.sort((a, b) -> Double.compare(b.getTotal(), a.getTotal()));
        return new ArrayList<>(competitors);
    }
}
