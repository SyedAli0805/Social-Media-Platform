package com.example.social.dto;

import lombok.*;

import java.time.Instant;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class PostResponse {
    private Long id;
    private Long userId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private long likeCount;
    private long commentCount;
}
