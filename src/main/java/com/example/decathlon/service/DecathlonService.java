package com.example.decathlon.service;

import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;
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
        int calculatedScore = 0;

        switch(event) {
            case "Dec_100m":
                calculatedScore = new Deca100M().calculateResult(raw);
                break;
            case "Dec_400m":
                calculatedScore = new Deca400M().calculateResult(raw);
                break;
            case "Dec_1500m":
                calculatedScore = new Deca1500M().calculateResult(raw);
                break;
            case "Dec_110mHurdles":
                calculatedScore = new Deca110MHurdles().calculateResult(raw);
                break;
            case "Dec_LongJump":
                calculatedScore = new DecaLongJump().calculateResult(raw);
                break;
            case "Dec_HighJump":
                calculatedScore = new DecaHighJump().calculateResult(raw);
                break;
            case "Dec_PoleVault":
                calculatedScore = new DecaPoleVault().calculateResult(raw);
                break;
            case "Dec_DiscusThrow":
                calculatedScore = new DecaDiscusThrow().calculateResult(raw);
                break;
            case "Dec_JavelinThrow":
                calculatedScore = new DecaJavelinThrow().calculateResult(raw);
                break;
            case "Dec_ShotPut":
                calculatedScore = new DecaShotPut().calculateResult(raw);
                break;
            // Heptathlon events
            case "Hep_200m":
                calculatedScore = new Hep200M().calculateResult(raw);
                break;
            case "Hep_800m":
                calculatedScore = new Hep800M().calculateResult(raw);
                break;
            case "Hep_100mHurdles":
                calculatedScore = new Hep100MHurdles().calculateResult(raw);
                break;
            case "Hep_HighJump":
                calculatedScore = new HeptHightJump().calculateResult(raw);
                break;
            case "Hep_LongJump":
                calculatedScore = new HeptLongJump().calculateResult(raw);
                break;
            case "Hep_ShotPut":
                calculatedScore = new HeptShotPut().calculateResult(raw);
                break;
            case "Hep_JavelinThrow":
                calculatedScore = new HeptJavelinThrow().calculateResult(raw);
                break;
        }

        // **Skicka beräknad poäng till WebCompetitor**
        competitor.addScore(event, (double) calculatedScore);
        return competitor;
    }



    public List<WebCompetitor> getStandings() {
        competitors.sort((a, b) -> Double.compare(b.getTotal(), a.getTotal()));
        return new ArrayList<>(competitors);
    }
}
