package com.example.social.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class AuthDto {
    @Getter @Setter
    public static class RegisterRequest {
        @NotBlank private String username;
        @Email @NotBlank private String email;
        @NotBlank @Size(min=6) private String password;
        private String bio;
        private String profilePictureUrl;
    }
    @Getter @Setter
    public static class LoginRequest {
        @NotBlank private String usernameOrEmail;
        @NotBlank private String password;
    }
    @Getter @AllArgsConstructor
    public static class JwtResponse {
        private String token;
    }
}
