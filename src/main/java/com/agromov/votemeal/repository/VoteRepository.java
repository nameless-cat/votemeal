package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.util.exception.BadArgumentException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 07.06.2017.
 */
public interface VoteRepository
{
    void addToVote(List<Restaurant> restaurants) throws Exception;

    int removeFromVote(long restaurantId);

    boolean increment(long restaurantId);

    boolean decrement(long restaurantId) throws BadArgumentException;

    List<Vote> getAll(LocalDate date);

    Vote get(LocalDate date, long restaurantId);
}
