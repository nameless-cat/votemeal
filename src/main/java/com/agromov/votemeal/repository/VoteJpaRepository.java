package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Transactional(readOnly = true)
public interface VoteJpaRepository extends JpaRepository<Vote, Vote.VotePK>
{
    @Query("SELECT v FROM Vote v WHERE v.pk.date=:date AND v.pk.restaurant.id=:id")
    Vote find(@Param("date") LocalDate date, @Param("id") long id);

    @Query("SELECT v FROM Vote v WHERE v.pk.date=:date")
    List<Vote> findByDate(@Param("date") LocalDate date);
}
