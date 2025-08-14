package com.example.social.controller;

import com.example.social.dto.*;
import com.example.social.model.User;
import com.example.social.repository.UserRepository;
import com.example.social.service.PostService;
import com.example.social.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/posts")
public class PostController {
    private final PostService svc;
    private final UserRepository users;
    public PostController(PostService svc, UserRepository users){ this.svc = svc; this.users = users; }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest r){
        User me = SecurityUtil.currentUser(users);
        return ResponseEntity.ok(svc.create(me, r));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> all(@RequestParam(defaultValue="0") int page,
                                                  @RequestParam(defaultValue="10") int size,
                                                  @RequestParam(defaultValue="createdAt,desc") String sort){
        String[] s = sort.split(",");
        Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(s[1]), s[0]));
        return ResponseEntity.ok(svc.all(p));
    }

    @GetMapping("/{id}") public ResponseEntity<PostResponse> get(@PathVariable Long id){ return ResponseEntity.ok(svc.get(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @Valid @RequestBody PostCreateRequest r){
        User me = SecurityUtil.currentUser(users);
        return ResponseEntity.ok(svc.update(me, id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        User me = SecurityUtil.currentUser(users);
        svc.delete(me, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> comment(@PathVariable Long id, @Valid @RequestBody CommentCreateRequest r){
        User me = SecurityUtil.currentUser(users);
        svc.comment(me, id, r);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id){
        User me = SecurityUtil.currentUser(users);
        svc.like(me, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PostResponse>> search(@RequestParam String q,
                                                     @RequestParam(defaultValue="0") int page,
                                                     @RequestParam(defaultValue="10") int size){
        Pageable p = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(svc.search(q, p));
    }
}
