package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.util.exception.BadArgumentException;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 14.06.2017.
 */
public interface VoteService
{
    List<Vote> get(LocalDate date);

    void add(List<Long> restaurantIds) throws Exception;

    void delete(long restaurantId) throws NotFoundException;

    void increment(long restaurantId, long userId) throws NotFoundException, VoteNotAcceptedException;

    void decrement(Long userId) throws VoteNotAcceptedException;
}
