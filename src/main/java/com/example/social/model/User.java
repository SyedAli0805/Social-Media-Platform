package com.example.social.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String username;
    @Column(nullable=false) private String email;
    @Column(nullable=false) private String passwordHash;

    private String profilePictureUrl;
    @Column(length=280) private String bio;
    private Instant createdAt = Instant.now();
}
