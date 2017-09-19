package com.agromov.votemeal.repository;

import static com.agromov.votemeal.RestaurantTestData.MCDONALDS;
import static com.agromov.votemeal.UserTestData.ADMIN_ID;
import static com.agromov.votemeal.UserTestData.MARIA_HISTORY;
import static com.agromov.votemeal.UserTestData.MARIA_ID;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.web.VoteTestData.CURRENT_VOTE_HISTORY;
import static org.junit.Assert.assertEquals;

import com.agromov.votemeal.model.VoteHistory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by A.Gromov on 07.06.2017.
 */
public class VoteRepositoryTest
    extends AbstractRepositoryTest {

  @Autowired
  private VoteRepository repository;

  @Test
  public void testGetUserHistory() throws Exception {
    List<VoteHistory> userHistory = repository.getUserHistory(MARIA_ID);
    assertEquals(MARIA_HISTORY, userHistory);
  }

  @Test
  public void testGetHistory() throws Exception {
    List<VoteHistory> history = repository.getHistory(currentDate());
    assertEquals(CURRENT_VOTE_HISTORY, history);
  }

  @Test
  public void testDelete() throws Exception {
    repository.delete(MARIA_ID);
    List<VoteHistory> currentVotes = new ArrayList<>(CURRENT_VOTE_HISTORY);
    currentVotes.remove(2);
    assertEquals(currentVotes, repository.getHistory(currentDate()));
  }

  @Test
  public void testSave() throws Exception {
    VoteHistory vh = new VoteHistory(currentDate(), MCDONALDS, ADMIN_ID);
    repository.save(vh);
    List<VoteHistory> currentVotes = new ArrayList<>(CURRENT_VOTE_HISTORY);
    currentVotes.add(vh);
    currentVotes.sort(Comparator.comparing((VoteHistory his) -> his.getRestaurant().getName())
        .thenComparing(VoteHistory::getUserId, (o1, o2) -> o1 > o2 ? -1 : 1));

    assertEquals(currentVotes, repository.getHistory(currentDate()));
  }
}
