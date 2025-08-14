package com.example.social.service;

import com.example.social.dto.UserResponse;
import com.example.social.model.*;
import com.example.social.exception.BadRequestException;
import com.example.social.exception.NotFoundException;
import com.example.social.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository users;
    private final FollowRepository follows;

    @Cacheable("userById")
    public UserResponse get(Long id){
        User u = users.findById(id).orElseThrow(() -> new NotFoundException("user not found"));
        return toDto(u);
    }

    @CacheEvict(value="userById", key="#id")
    public void clearUserCache(Long id) {}

    public void follow(User follower, Long idToFollow){
        if (follower.getId().equals(idToFollow)) throw new BadRequestException("cannot follow self");
        User target = users.findById(idToFollow).orElseThrow(() -> new NotFoundException("user not found"));
        if (follows.existsByFollowerAndFollowing(follower, target)) return;
        follows.save(Follow.builder().follower(follower).following(target).build());
    }

    public Page<UserResponse> search(String q, Pageable pageable){
        var page = users.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrBioContainingIgnoreCase(q, q, q, pageable);
        return page.map(this::toDto);
    }

    public List<User> following(User u){
        return follows.findByFollower(u).stream().map(Follow::getFollowing).toList();
    }
    public List<User> followers(User u){
        return follows.findByFollowing(u).stream().map(Follow::getFollower).toList();
    }

    private UserResponse toDto(User u){
        return UserResponse.builder()
                .id(u.getId()).username(u.getUsername()).email(u.getEmail())
                .bio(u.getBio()).profilePictureUrl(u.getProfilePictureUrl()).createdAt(u.getCreatedAt())
                .build();
    }
}
