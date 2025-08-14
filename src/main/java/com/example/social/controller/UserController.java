package com.example.social.controller;

import com.example.social.dto.UserResponse;
import com.example.social.model.User;
import com.example.social.repository.UserRepository;
import com.example.social.service.UserService;
import com.example.social.util.SecurityUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/users")
public class UserController {
    private final UserService svc;
    private final UserRepository users;
    public UserController(UserService svc, UserRepository users){ this.svc = svc; this.users = users; }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id){ return ResponseEntity.ok(svc.get(id)); }

    @PostMapping("/{id}/follow")
    public ResponseEntity<Void> follow(@PathVariable Long id){
        User me = SecurityUtil.currentUser(users);
        svc.follow(me, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<UserResponse>> followers(@PathVariable Long id){
        var user = users.findById(id).orElseThrow();
        var list = svc.followers(user).stream().map(u -> svc.get(u.getId())).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<UserResponse>> following(@PathVariable Long id){
        var user = users.findById(id).orElseThrow();
        var list = svc.following(user).stream().map(u -> svc.get(u.getId())).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<UserResponse>> search(@RequestParam @NotBlank String q,
                                                     @RequestParam(defaultValue="0") int page,
                                                     @RequestParam(defaultValue="10") int size){
        Pageable p = PageRequest.of(page, size, Sort.by("username").ascending());
        return ResponseEntity.ok(svc.search(q, p));
    }
}
