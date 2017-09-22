package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.VoteHistory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 07.06.2017.
 */
public interface VoteRepository {

  void save(VoteHistory voteHistory);

  int delete(Long userId);

  List<VoteHistory> getUserHistory(Long userId);

  List<VoteHistory> getHistory(LocalDate date);

  void update(VoteHistory voteHistory);
}
