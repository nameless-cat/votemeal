package com.agromov.votemeal.web;

import static com.agromov.votemeal.RestaurantTestData.BENJAMIN;
import static com.agromov.votemeal.RestaurantTestData.CHOCO;
import static com.agromov.votemeal.RestaurantTestData.MCDONALDS;
import static com.agromov.votemeal.RestaurantTestData.POTATO;
import static com.agromov.votemeal.RestaurantTestData.SUBWAY;
import static com.agromov.votemeal.UserTestData.MARIA_ID;
import static com.agromov.votemeal.UserTestData.OLEG_ID;
import static com.agromov.votemeal.UserTestData.USER_ID;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

import com.agromov.votemeal.matchers.ModelMatcher;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.model.VoteHistory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by A.Gromov on 10.06.2017.
 */
public class VoteTestData {

  static final ModelMatcher<Vote> MATCHER = ModelMatcher.of(Vote.class,
      (expected, actual) -> expected == actual ||
          Objects.equals(expected.getDate(), actual.getDate())
              && Objects.equals(expected.getVotes(), actual.getVotes())
              && Objects
              .equals(expected.getRestaurant().getName(), actual.getRestaurant().getName())
              && Objects
              .equals(expected.getRestaurant().getAddress(), actual.getRestaurant().getAddress())
  );


  private static final LocalDate TEST_DATE = LocalDate.parse("2014-05-20");


  static final Vote POTATO_VOTE_PAST = new Vote(TEST_DATE, POTATO, 2);
  static final Vote MCDONALDS_VOTE_PAST = new Vote(TEST_DATE, MCDONALDS, 0);

  static final Vote MCDONALDS_VOTE = new Vote(currentDate(), MCDONALDS, 0);
  static final Vote POTATO_VOTE = new Vote(currentDate(), POTATO, 0);
  static final Vote CHOCO_VOTE = new Vote(currentDate(), CHOCO, 2);
  static final Vote BENJAMIN_VOTE = new Vote(currentDate(), BENJAMIN, 0);
  static final Vote SUBWAY_VOTE = new Vote(currentDate(), SUBWAY, 1);

  public static final List<VoteHistory> CURRENT_VOTE_HISTORY = new ArrayList<>();

  static {
    CURRENT_VOTE_HISTORY.add(new VoteHistory(currentDate(), SUBWAY, USER_ID));
    CURRENT_VOTE_HISTORY.add(new VoteHistory(currentDate(), CHOCO, OLEG_ID));
    CURRENT_VOTE_HISTORY.add(new VoteHistory(currentDate(), CHOCO, MARIA_ID));
  }
}
