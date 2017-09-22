package com.agromov.votemeal.web;

import static com.agromov.votemeal.RestaurantTestData.CHOCO;
import static com.agromov.votemeal.RestaurantTestData.POTATO;
import static com.agromov.votemeal.RestaurantTestData.SUBWAY;
import static com.agromov.votemeal.UserTestData.MARIA_ID;
import static com.agromov.votemeal.UserTestData.OLEG_ID;
import static com.agromov.votemeal.UserTestData.USER_ID;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.VoteHistory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A.Gromov on 10.06.2017.
 */
public class VoteTestData {

  public static final List<VoteHistory> CURRENT_VOTE_HISTORY = new ArrayList<>();

  static {
    CURRENT_VOTE_HISTORY.add(new VoteHistory(currentDate(), SUBWAY, USER_ID));
    CURRENT_VOTE_HISTORY.add(new VoteHistory(currentDate(), CHOCO, OLEG_ID));
    CURRENT_VOTE_HISTORY.add(new VoteHistory(currentDate(), CHOCO, MARIA_ID));
  }

  public static final List<Restaurant> CURRENT_VOTE_LIST = new ArrayList<>();

  static {
    CURRENT_VOTE_LIST.add(SUBWAY);
    CURRENT_VOTE_LIST.add(CHOCO);
  }

  public static final List<Restaurant> PAST_VOTE_LIST = new ArrayList<>();

  static {
    PAST_VOTE_LIST.add(POTATO);
  }
}
