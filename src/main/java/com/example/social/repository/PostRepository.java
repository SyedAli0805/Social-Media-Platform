package com.example.social.repository;
import com.example.social.model.Post;
import com.example.social.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserIn(Iterable<User> users, Pageable pageable);
    Page<Post> findByContentContainingIgnoreCase(String q, Pageable pageable);
}
