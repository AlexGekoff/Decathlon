package com.example.decathlon.controller;

import com.example.decathlon.model.WebCompetitor;
import com.example.decathlon.service.DecathlonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class DecathlonController {
    private final DecathlonService service;

    public DecathlonController(DecathlonService service) {
        this.service = service;
}
    @PostMapping("/competitors")
    public WebCompetitor addCompetitor(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        return service.addCompetitor(name);
    }

    @PostMapping("/score")
    public WebCompetitor addScore(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String event = (String) body.get("event");
        double raw = ((Number) body.get("raw")).doubleValue();
        return service.addScore(name, event, raw);
    }

    @GetMapping("/standings")
    public List<WebCompetitor> standings() {
        return service.getStandings();
    }
}
