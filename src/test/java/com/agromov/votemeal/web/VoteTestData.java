package com.agromov.votemeal.web;

import com.agromov.votemeal.matchers.ModelMatcher;
import com.agromov.votemeal.model.Vote;

import java.time.LocalDate;
import java.util.Objects;

import static com.agromov.votemeal.RestaurantTestData.*;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 10.06.2017.
 */
public class VoteTestData
{
    public static final ModelMatcher<Vote> MATCHER = ModelMatcher.of(Vote.class,
            (expected, actual) -> expected == actual ||
                            Objects.equals(expected.getDate(), actual.getDate())
                            && Objects.equals(expected.getVotes(), actual.getVotes())
                            && Objects.equals(expected.getRestaurant(), actual.getRestaurant())
    );


    public static final LocalDate TEST_DATE = LocalDate.parse("2014-05-20");

    public static final Vote MCDONALDS_VOTE = new Vote(currentDate(), MCDONALDS, 0);
    public static final Vote POTATO_VOTE = new Vote(currentDate(), POTATO, 4);
    public static final Vote CHOCO_VOTE = new Vote(currentDate(), CHOCO, 60);
    public static final Vote BENJAMIN_VOTE = new Vote(currentDate(), BENJAMIN, 0);
    public static final Vote SUBWAY_VOTE = new Vote(currentDate(), SUBWAY, 12);
}
