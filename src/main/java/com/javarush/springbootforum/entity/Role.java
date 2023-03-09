package com.javarush.springbootforum.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    MODERATOR,
    USER,
    GUEST;

    @Override
    public String getAuthority() {
        return name();
    }


}
