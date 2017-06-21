package com.agromov.votemeal.util;

import com.agromov.votemeal.model.User;

/**
 * Created by A.Gromov on 21.06.2017.
 */
public class UserUtils
{
    public static User prepareToSave(User user)
    {
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(PasswordUtils.encode(user.getPassword()));
        return user;
    }
}
