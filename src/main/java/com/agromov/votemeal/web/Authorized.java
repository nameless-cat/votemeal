package com.agromov.votemeal.web;

import com.agromov.votemeal.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * Created by A.Gromov on 12.06.2017.
 */
public class Authorized implements UserDetails
{
    private static final long serialVersionUID = 1L;
    private User user;

    public Authorized(User user) {
        this.user = user;
    }

    public static Authorized safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof Authorized) ? (Authorized) principal : null;
    }

    public static Authorized get() {
        Authorized user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static User getUser()
    {
        return get().user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return user.getRoles();
    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return isEnabled();
    }

    @Override
    public boolean isEnabled()
    {
        return user.isEnabled();
    }
}
