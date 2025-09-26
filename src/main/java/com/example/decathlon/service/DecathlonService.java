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
        int score = 0;

        switch(event) {
            case "Hep200m":
                Hep200M hep200M = new Hep200M();
                score = hep200M.calculateResult(raw);
                break;
            case "Hep800m":
                Hep800M hep800M = new Hep800M();
                score = hep800M.calculateResult(raw);
                break;
            case "Hep100mHurdles":
                Hep100MHurdles hep100MHurdles = new Hep100MHurdles();
                score = hep100MHurdles.calculateResult(raw);
                break;
            case "HepHighJump":
                HeptHightJump heptHightJump = new HeptHightJump();
                score = heptHightJump.calculateResult(raw);
                break;
            case "HepLongJump":
                HeptLongJump heptLongJump = new HeptLongJump();
                score = heptLongJump.calculateResult(raw);
                break;
            case "HepShotPut":
                HeptShotPut heptShotPut = new HeptShotPut();
                score = heptShotPut.calculateResult(raw);
                break;
            case "HepJavelinThrow":
                HeptJavelinThrow heptJavelinThrow = new HeptJavelinThrow();
                score = heptJavelinThrow.calculateResult(raw);
                break;
            case "1500m":
                Deca1500M deca1500M = new Deca1500M();
                score = deca1500M.calculateResult(raw);
                break;
            case "JavelinThrow":
                DecaJavelinThrow decaJavelinThrow = new DecaJavelinThrow();
                score = decaJavelinThrow.calculateResult(raw);
                break;
            case "PoleVault":
                DecaPoleVault decaPoleVault = new DecaPoleVault();
                score = decaPoleVault.calculateResult(raw);
                break;
            case "DiscusThrow":
                DecaDiscusThrow decaDiscusThrow = new DecaDiscusThrow();
                score = decaDiscusThrow.calculateResult(raw);
                break;
            case "110mHurdles":
                Deca110MHurdles deca110MHurdles = new Deca110MHurdles();
                score = deca110MHurdles.calculateResult(raw);
                break;
            case "highJump":
                DecaHighJump decaHighJump = new DecaHighJump();
                score = decaHighJump.calculateResult(raw);
                break;
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
