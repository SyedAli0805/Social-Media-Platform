package com.example.social.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id","following_id"}))
public class Follow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(optional=false) @JoinColumn(name = "following_id")
    private User following;

    private Instant createdAt = Instant.now();
}