package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.VoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.Gromov on 13.06.2017.
 */
@Transactional(readOnly = true)
public interface VoteHistoryJpaRepository
        extends JpaRepository<VoteHistory, Long>
{
    List<VoteHistory> findAllByUserIdOrderByDateDesc(Long id);
}