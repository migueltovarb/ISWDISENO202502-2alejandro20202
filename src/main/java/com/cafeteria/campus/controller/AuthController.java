package com.cafeteria.campus.controller;

import com.cafeteria.campus.model.User;
import com.cafeteria.campus.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User u = authService.register(req.email, req.fullName, req.password);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User u = authService.validateUser(req.email, req.password);
        if (u == null) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(u);
    }

    record RegisterRequest(String email, String fullName, String password) {}
    record LoginRequest(String email, String password) {}
}
