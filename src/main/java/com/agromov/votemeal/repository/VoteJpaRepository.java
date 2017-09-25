package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.VoteHistory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Transactional(readOnly = true)
public interface VoteJpaRepository extends
    JpaRepository<VoteHistory, VoteHistory.Key> {

  @Transactional
  @Modifying
  @Query("DELETE FROM VoteHistory v WHERE v.key.userId=:userId AND v.key.date=:date")
  int deleteFromHistory(@Param("userId") Long userId, @Param("date") LocalDate date);

  @Query("SELECT v FROM VoteHistory v WHERE v.key.userId=:userId")
  List<VoteHistory> getVoteHistoryByUserId(@Param("userId") Long userId);

  @Query("SELECT v FROM VoteHistory v WHERE v.key.date=:date")
  List<VoteHistory> getVoteHistoryByDate(@Param("date") LocalDate date);

  @Transactional
  @Modifying
  @Query("UPDATE VoteHistory v SET v.key.restaurant=:restaurant WHERE v.key.date=:date AND v.key.userId=:userId")
  void update(
      @Param("restaurant") Restaurant restaurant,
      @Param("userId") Long userId,
      @Param("date") LocalDate date);

  @Query("SELECT v FROM VoteHistory v WHERE v.key.date=:date AND v.key.userId=:id")
  VoteHistory getCurrentUserVote(@Param("id") Long userId, @Param("date") LocalDate date);
}
