package com.spring.exercise;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface UserDetails {

    public Collection<? extends GrantedAuthority> getAuthorities();

    public String getUsername();

    public boolean isAccountNonExpired();

    public boolean isAccountNonLocked();

    public boolean isCredentialsExpired();

    public boolean isEnabled();
}
