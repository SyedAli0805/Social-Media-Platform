package com.example.social.service;

import com.example.social.dto.*;
import com.example.social.model.*;
import com.example.social.exception.BadRequestException;
import com.example.social.exception.NotFoundException;
import com.example.social.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service @RequiredArgsConstructor
public class PostService {
    private final PostRepository posts;
    private final CommentRepository comments;
    private final PostLikeRepository likes;

    @Transactional
    public PostResponse create(User me, PostCreateRequest r){
        Post p = Post.builder().user(me).content(r.getContent()).createdAt(Instant.now()).build();
        posts.save(p);
        return toDto(p, 0, 0);
    }

    @Cacheable("postById")
    public PostResponse get(Long id){
        Post p = posts.findById(id).orElseThrow(() -> new NotFoundException("post not found"));
        long lc = likes.countByPost(p);
        long cc = p.getComments().size();
        return toDto(p, lc, cc);
    }

    public Page<PostResponse> all(Pageable pageable){
        return posts.findAll(pageable).map(p -> toDto(p, p.getLikes().size(), p.getComments().size()));
    }

    @Transactional
    @CacheEvict(value="postById", key="#id")
    public PostResponse update(User me, Long id, PostCreateRequest r){
        Post p = posts.findById(id).orElseThrow(() -> new NotFoundException("post not found"));
        if (!p.getUser().getId().equals(me.getId())) throw new BadRequestException("not owner");
        p.setContent(r.getContent());
        p.setUpdatedAt(java.time.Instant.now());
        return toDto(p, p.getLikes().size(), p.getComments().size());
    }

    @Transactional
    @CacheEvict(value="postById", key="#id")
    public void delete(User me, Long id){
        Post p = posts.findById(id).orElseThrow(() -> new NotFoundException("post not found"));
        if (!p.getUser().getId().equals(me.getId())) throw new BadRequestException("not owner");
        posts.delete(p);
    }

    @Transactional
    @CacheEvict(value="postById", key="#postId")
    public void like(User me, Long postId){
        Post p = posts.findById(postId).orElseThrow(() -> new NotFoundException("post not found"));
        if (likes.findByUserAndPost(me, p).isPresent()) return;
        likes.save(PostLike.builder().user(me).post(p).build());
    }

    @Transactional
    @CacheEvict(value="postById", key="#postId")
    public void comment(User me, Long postId, CommentCreateRequest r){
        Post p = posts.findById(postId).orElseThrow(() -> new NotFoundException("post not found"));
        comments.save(Comment.builder().user(me).post(p).content(r.getContent()).build());
    }

    public Page<PostResponse> search(String q, Pageable pageable){
        return posts.findByContentContainingIgnoreCase(q, pageable)
                .map(p -> toDto(p, p.getLikes().size(), p.getComments().size()));
    }

    private PostResponse toDto(Post p, long likeCount, long commentCount){
        return PostResponse.builder()
                .id(p.getId()).userId(p.getUser().getId()).content(p.getContent())
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .likeCount(likeCount).commentCount(commentCount)
                .build();
    }
}
