package com.agromov.votemeal;

import com.agromov.votemeal.Util.UserBuilder;
import com.agromov.votemeal.matchers.ModelMatcher;
import com.agromov.votemeal.model.Role;
import com.agromov.votemeal.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Objects;

/**
 * Created by A.Gromov on 23.05.2017.
 */
public class UserTestData
{
    public static final User MARIA = new UserBuilder()
            .withId(2)
            .withName("Maria")
            .withEmail("maria@yandex.ru")
            .withPassword("password")
            .withEnabled(true)
            .withDate(LocalDateTime.parse("2015-04-23 17:15:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .withRoles(Role.ROLE_USER)
            .build();

    public static final int MARIA_ID = MARIA.getId();

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getRegistered(), actual.getRegistered())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );
}
