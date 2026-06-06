package com.boardinghouse.boardinghouse_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestConnection {
    @GetMapping("/test")
    public String getHello() {
        return "Hello, the endpoint works!";
    }
    
}
