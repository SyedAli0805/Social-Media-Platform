package com.example.social.util;

import com.example.social.model.User;
import com.example.social.exception.NotFoundException;
import com.example.social.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static User currentUser(UserRepository users){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String username = a.getName();
        return users.findByUsername(username).orElseThrow(() -> new NotFoundException("user not found"));
    }
}
