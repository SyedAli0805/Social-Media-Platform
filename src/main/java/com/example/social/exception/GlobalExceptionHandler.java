package com.example.social.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> nf(NotFoundException e){ return ResponseEntity.status(404).body(Map.of("error", e.getMessage())); }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> br(BadRequestException e){ return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> val(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(Map.of("error", "validation_failed"));
    }
}
