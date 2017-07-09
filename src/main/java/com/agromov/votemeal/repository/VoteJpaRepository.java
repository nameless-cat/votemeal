package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface VoteJpaRepository extends JpaRepository<Vote, Long>
{
    @Query("SELECT v FROM Vote v WHERE v.date=:date AND v.restaurant.id=:id")
    Vote find(@Param("date") LocalDate date, @Param("id") long id);

    @EntityGraph(attributePaths = "restaurant")
    @Query("SELECT v FROM Vote v WHERE v.date=:date")
    List<Vote> findByDate(@Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.restaurant.id=:id AND v.date=:date")
    int remove(@Param("id") long restaurantId, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.votes=v.votes + 1 WHERE v.date=:date AND v.restaurant.id=:id")
    int increment(@Param("date") LocalDate date, @Param("id") long restaurantId);
}
