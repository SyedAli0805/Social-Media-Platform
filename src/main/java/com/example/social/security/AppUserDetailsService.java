package com.example.social.security;

import com.example.social.model.User;
import com.example.social.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public AppUserDetailsService(UserRepository users) { this.users = users; }

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new AppUserDetails(u);
    }

    public UserDetails loadByEmail(String email) {
        User u = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new AppUserDetails(u);
    }
}
