package com.example.decathlon.service;

import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;
import com.example.decathlon.model.WebCompetitor;
import com.example.decathlon.common.ValidationUtil; // 🔹 importera valideringen

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

        try {
            switch(event) {
                case "Dec_100m":
                    ValidationUtil.validateResult("Deca 100m", raw, 5, 17.8);
                    calculatedScore = new Deca100M().calculateResult(raw);
                    break;
                case "Dec_400m":
                    ValidationUtil.validateResult("Deca 400m", raw, 40, 80);
                    calculatedScore = new Deca400M().calculateResult(raw);
                    break;
                case "Dec_1500m":
                    ValidationUtil.validateResult("Deca 1500m", raw, 230, 400);
                    calculatedScore = new Deca1500M().calculateResult(raw);
                    break;
                case "Dec_110mHurdles":
                    ValidationUtil.validateResult("Deca 110m Hurdles", raw, 13, 25);
                    calculatedScore = new Deca110MHurdles().calculateResult(raw);
                    break;
                case "Dec_LongJump":
                    ValidationUtil.validateResult("Deca Long Jump", raw, 5, 9);
                    calculatedScore = new DecaLongJump().calculateResult(raw);
                    break;
                case "Dec_HighJump":
                    ValidationUtil.validateResult("Deca High Jump", raw, 1, 2.5);
                    calculatedScore = new DecaHighJump().calculateResult(raw);
                    break;
                case "Dec_PoleVault":
                    ValidationUtil.validateResult("Deca Pole Vault", raw, 3, 6.5);
                    calculatedScore = new DecaPoleVault().calculateResult(raw);
                    break;
                case "Dec_DiscusThrow":
                    ValidationUtil.validateResult("Deca Discus Throw", raw, 20, 70);
                    calculatedScore = new DecaDiscusThrow().calculateResult(raw);
                    break;
                case "Dec_JavelinThrow":
                    ValidationUtil.validateResult("Deca Javelin Throw", raw, 30, 90);
                    calculatedScore = new DecaJavelinThrow().calculateResult(raw);
                    break;
                case "Dec_ShotPut":
                    ValidationUtil.validateResult("Deca Shot Put", raw, 0, 30);
                    calculatedScore = new DecaShotPut().calculateResult(raw);
                    break;
                // Heptathlon events
                case "Hep_200m":
                    ValidationUtil.validateResult("Hep 200m", raw, 14, 42.08);
                    calculatedScore = new Hep200M().calculateResult(raw);
                    break;
                case "Hep_800m":
                    ValidationUtil.validateResult("Hep 800m", raw, 70, 250.79);
                    calculatedScore = new Hep800M().calculateResult(raw);
                    break;
                case "Hep_100mHurdles":
                    ValidationUtil.validateResult("Hep 100m Hurdles", raw, 5, 26.4);
                    calculatedScore = new Hep100MHurdles().calculateResult(raw);
                    break;
                case "Hep_HighJump":
                    ValidationUtil.validateResult("Hep High Jump", raw, 75.7, 270);
                    calculatedScore = new HeptHightJump().calculateResult(raw);
                    break;
                case "Hep_LongJump":
                    ValidationUtil.validateResult("Hep Long Jump", raw, 0, 400);
                    calculatedScore = new HeptLongJump().calculateResult(raw);
                    break;
                case "Hep_ShotPut":
                    ValidationUtil.validateResult("Hep Shot Put", raw, 5, 100);
                    calculatedScore = new HeptShotPut().calculateResult(raw);
                    break;
                case "Hep_JavelinThrow":
                    ValidationUtil.validateResult("Hep Javelin Throw", raw, 0, 100);
                    calculatedScore = new HeptJavelinThrow().calculateResult(raw);
                    break;
            }

            competitor.addScore(event, (double) calculatedScore);
            return competitor;

        } catch (IllegalArgumentException ex) {
            // 🔹 låt exceptionen bubbla upp till GlobalExceptionHandler
            throw ex;}
    }

    public List<WebCompetitor> getStandings() {
        competitors.sort((a, b) -> Double.compare(b.getTotal(), a.getTotal()));
        return new ArrayList<>(competitors);
    }
}
