package com.example.decathlon.service;

import com.example.decathlon.model.WebCompetitor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DecathlonService {
    private final Map<String, WebCompetitor> competitors = new HashMap<>();

    public WebCompetitor addCompetitor(String name) {
        WebCompetitor c = new WebCompetitor(name);
        competitors.put(name, c);
        return c;
    }

    public WebCompetitor addScore(String name, String event, double raw) {
        WebCompetitor c = competitors.get(name);
        if (c == null) {
            throw new RuntimeException("Competitor not found: " + name);
        }
        c.addScore(event, raw);
        return c;
    }

    public List<WebCompetitor> getStandings() {
        return new ArrayList<>(competitors.values());
    }
}
