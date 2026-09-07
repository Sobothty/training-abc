package com.example.trainingfullstack.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetailResponse implements UserDetails {

    private final String uuid;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    public CustomUserDetailResponse(String uuid, String username, String password, String role) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        // FIX: wrap the String role in SimpleGrantedAuthority inside a List
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
