package com.example.social.service;

import com.example.social.dto.AuthDto.*;
import com.example.social.model.User;
import com.example.social.exception.BadRequestException;
import com.example.social.repository.UserRepository;
import com.example.social.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service @RequiredArgsConstructor
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String register(RegisterRequest r){
        if (users.findByUsername(r.getUsername()).isPresent()) throw new BadRequestException("username taken");
        if (users.findByEmail(r.getEmail()).isPresent()) throw new BadRequestException("email taken");
        User u = User.builder()
                .username(r.getUsername())
                .email(r.getEmail())
                .passwordHash(encoder.encode(r.getPassword()))
                .bio(r.getBio())
                .profilePictureUrl(r.getProfilePictureUrl())
                .createdAt(Instant.now())
                .build();
        users.save(u);
        return jwt.generate(u.getUsername());
    }

    public String login(LoginRequest r){
        User u = users.findByUsername(r.getUsernameOrEmail())
                .orElseGet(() -> users.findByEmail(r.getUsernameOrEmail()).orElse(null));
        if (u == null) throw new BadRequestException("invalid credentials");
        if (!encoder.matches(r.getPassword(), u.getPasswordHash())) throw new BadRequestException("invalid credentials");
        return jwt.generate(u.getUsername());
    }
}
