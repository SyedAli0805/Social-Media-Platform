package com.example.social.security;

import com.example.social.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

public record AppUserDetails(User user) implements UserDetails {
    @Override public List<SimpleGrantedAuthority> getAuthorities() { return List.of(new SimpleGrantedAuthority("USER")); }
    @Override public String getPassword() { return user.getPasswordHash(); }
    @Override public String getUsername() { return user.getUsername(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
