package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 14.06.2017.
 */
public interface VoteService
{
    List<Vote> get(LocalDate date);

    void add(List<Long> restaurantIds);

    void delete(long restaurantId) throws EntityNotFoundException;

    void increment(long restaurantId, long userId) throws EntityNotFoundException, VoteNotAcceptedException;

    void decrement(Long userId) throws VoteNotAcceptedException;
}
