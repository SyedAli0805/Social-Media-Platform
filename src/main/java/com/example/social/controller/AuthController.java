package com.example.social.controller;

import com.example.social.dto.AuthDto.*;
import com.example.social.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/users")
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest req){
        return ResponseEntity.ok(new JwtResponse(auth.register(req)));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest req){
        return ResponseEntity.ok(new JwtResponse(auth.login(req)));
    }
}
