package com.agromov.votemeal.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by A.Gromov on 22.05.2017.
 */
public enum Role implements GrantedAuthority
{
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority()
    {
        return name();
    }
}
