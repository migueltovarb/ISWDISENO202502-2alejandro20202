package com.cafeteria.campus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/")
    public String home() {
        return "Backend Cafetería Campus corriendo 🚀 Usa /api/... para consumir la API";
    }
}
