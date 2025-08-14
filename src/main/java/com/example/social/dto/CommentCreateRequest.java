package com.example.social.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Getter @Setter
public class CommentCreateRequest {
    @NotBlank @Size(max=1000) private String content;
}
