package com.agromov.votemeal.util;

import com.agromov.votemeal.model.Role;
import com.agromov.votemeal.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * Created by A.Gromov on 24.05.2017.
 */
public class UserBuilder
{
    private User user;


    public UserBuilder()
    {
        user = new User();
        user.setEnabled(true);
        user.setRoles(EnumSet.of(Role.ROLE_USER));
    }

    public UserBuilder(User user)
    {
        this.user = new User();
        this.user.setId(user.getId());
        this.user.setName(user.getName());
        this.user.setEmail(user.getEmail());
        this.user.setPassword(user.getPassword());
        this.user.setRegistered(user.getRegistered());
        this.user.setRoles(user.getRoles());
        this.user.setEnabled(user.isEnabled());
    }

    public UserBuilder withId(int id)
    {
        user.setId(id);
        return this;
    }

    public UserBuilder withName(String name)
    {
        user.setName(name);
        return this;
    }

    public UserBuilder withEmail(String email)
    {
        user.setEmail(email);
        return this;
    }

    public UserBuilder withPassword(String password)
    {
        user.setPassword(password);
        return this;
    }

    public UserBuilder withDate(LocalDateTime dateTime)
    {
        user.setRegistered(dateTime);
        return this;
    }

    public UserBuilder withRoles(Role... roles)
    {
        user.setRoles(Arrays.asList(roles));
        return this;
    }

    public UserBuilder withEnabled(boolean enabled)
    {
        user.setEnabled(enabled);
        return this;
    }

    public User build()
    {
        return user;
    }
}
