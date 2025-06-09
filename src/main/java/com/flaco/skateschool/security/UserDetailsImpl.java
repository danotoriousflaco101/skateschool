package com.flaco.skateschool.security;

import com.flaco.skateschool.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User user; // Reference to User entity

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return user role as a GrantedAuthority
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    } // Return user's password

    @Override
    public String getUsername() {
        return user.getUsername();
    } // Return user's username

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // Account is never expired

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // Account is never locked

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // Credentials never expire

    @Override
    public boolean isEnabled() {
        return user.isActive();
    } // Check if user is active
}