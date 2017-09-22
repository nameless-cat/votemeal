package com.agromov.votemeal.repository;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

import com.agromov.votemeal.model.VoteHistory;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Repository
public class VoteRepositoryImpl
    implements VoteRepository {

  @Autowired
  private VoteJpaRepository voteRepository;

  @Override
  public void save(VoteHistory voteHistory) {
    voteRepository.save(voteHistory);
  }

  @Override
  public int delete(Long userId) {
    return voteRepository.deleteFromHistory(userId, currentDate());
  }

  @Override
  public List<VoteHistory> getUserHistory(Long userId) {
    return voteRepository.getVoteHistoryByUserId(userId).stream()
        .sorted(Comparator.comparing(VoteHistory::getDate))
        .collect(Collectors.toList());
  }

  @Override
  public List<VoteHistory> getHistory(LocalDate date) {
    // Упорядочивание по имени ресторана и userId в убывающем
    return voteRepository.getVoteHistoryByDate(date).stream()
        .sorted(Comparator.comparing((VoteHistory vh) -> vh.getRestaurant().getName())
            .thenComparing(VoteHistory::getUserId, (o1, o2) -> o1 > o2 ? -1 : 1))
        .collect(Collectors.toList());
  }

  @Override
  public void update(VoteHistory voteHistory) {
    voteRepository.update(
        voteHistory.getRestaurant(),
        voteHistory.getUserId(),
        voteHistory.getDate());
  }
}
