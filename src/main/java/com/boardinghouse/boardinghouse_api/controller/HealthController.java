package com.boardinghouse.boardinghouse_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "message", "Boarding House API is running"
        );
    }
    
    @GetMapping("/api/test")
    public Map<String, String> test() {
        return Map.of(
            "message", "API is accessible",
            "cors", "Configured properly"
        );
    }
}