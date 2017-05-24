package com.agromov.votemeal.Util;

import com.agromov.votemeal.model.Role;
import com.agromov.votemeal.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by A.Gromov on 24.05.2017.
 */
public class UserBuilder
{
    private User user;


    public UserBuilder()
    {
        user = new User();
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
