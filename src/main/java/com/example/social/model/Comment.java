package com.example.social.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable=false, length=1000)
    private String content;

    private Instant createdAt = Instant.now();
}
