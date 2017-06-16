package com.agromov.votemeal;

import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.util.DateTimeUtil;
import com.agromov.votemeal.util.UserBuilder;
import com.agromov.votemeal.matchers.ModelMatcher;
import com.agromov.votemeal.model.Role;
import com.agromov.votemeal.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.agromov.votemeal.RestaurantTestData.*;

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
            .withDate(DateTimeUtil.parse("2015-04-23 17:15:02"))
            .build();

    public static final User OLEG = new UserBuilder()
            .withId(3)
            .withName("Oleg")
            .withEmail("oleg@yandex.ru")
            .withPassword("psswd")
            .withDate(DateTimeUtil.parse("2015-05-15 14:15:00"))
            .build();

    public static final User ADMIN = new UserBuilder()
            .withId(1)
            .withName("Admin")
            .withEmail("admin@gmail.com")
            .withPassword("admin")
            .withDate(DateTimeUtil.parse("2015-01-01 00:00:00"))
            .withEnabled(true)
            .withRoles(Role.ROLE_ADMIN)
            .build();

    public static final long MARIA_ID = MARIA.getId();
    public static final long OLEG_ID = OLEG.getId();
    public static final long ADMIN_ID = ADMIN.getId();

    public static final List<VoteHistory> MARIA_HISTORY  = new ArrayList<>(Arrays.asList(
            new VoteHistory(LocalDate.now(), CHOCO, MARIA_ID),
            new VoteHistory(LocalDate.parse("2014-05-20"), POTATO, MARIA_ID)
    ));

    public static final ModelMatcher<VoteHistory> VOTE_MATCHER = ModelMatcher.of(VoteHistory.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDate(), actual.getDate())
                    && Objects.equals(expected.getRestaurant().getName(), actual.getRestaurant().getName())
                    && Objects.equals(expected.getRestaurant().getAddress().getStreet(), actual.getRestaurant().getAddress().getStreet())
                    && Objects.equals(expected.getRestaurant().getAddress().getBuilding(), actual.getRestaurant().getAddress().getBuilding()))
            );

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

    public static User getUpdated()
    {
        return new UserBuilder(MARIA).withEmail("masha@mail.ru").build();
    }

    public static User getCreated()
    {
        return new UserBuilder()
                .withName("New user")
                .withDate(LocalDateTime.now())
                .withEmail("new@gmail.com")
                .withPassword("psssswwwd")
                .build();
    }
}
