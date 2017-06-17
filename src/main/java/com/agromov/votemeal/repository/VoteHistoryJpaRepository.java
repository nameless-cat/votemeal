package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.VoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 13.06.2017.
 */
@Transactional(readOnly = true)
public interface VoteHistoryJpaRepository
        extends JpaRepository<VoteHistory, Long>
{
    List<VoteHistory> findAllByUserIdOrderByDateDesc(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM VoteHistory vh WHERE vh.userId=:id AND vh.date=:date")
    void delete(@Param("id") long userId, @Param("date") LocalDate date);

    @Transactional
    @Override
    VoteHistory save(VoteHistory vh);
}