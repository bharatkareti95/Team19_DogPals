package com.dogpals.training.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SamAuthenticationToken extends UsernamePasswordAuthenticationToken {

public Long getUserId() {
    return userId;
}

private final Long userId;

public SamAuthenticationToken(Object principal, Object credentials, Long userId) {
    super(principal, credentials);
    this.userId = userId;
}

public SamAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long userId) {
    super(principal, credentials, authorities);
    this.userId = userId;
}
}