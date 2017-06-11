package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.SimpleVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Created by A.Gromov on 10.06.2017.
 */
@Transactional(readOnly = true)
public interface SimpleVoteRepository
        extends JpaRepository<SimpleVote, SimpleVote.SimpleVotePK>
{
    @Transactional
    SimpleVote save(SimpleVote vote);

    @Transactional
    @Modifying
    @Query("DELETE FROM SimpleVote v WHERE v.pk.restaurantId=:id AND v.pk.date=:date")
    int remove(@Param("id") long restaurantId, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("UPDATE SimpleVote v SET v.votes=v.votes + 1 WHERE v.pk.date=:date AND v.pk.restaurantId=:id")
    int increment(@Param("date") LocalDate date, @Param("id") long restaurantId);
}
