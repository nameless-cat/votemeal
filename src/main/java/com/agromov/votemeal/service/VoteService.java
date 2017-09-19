package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by A.Gromov on 14.06.2017.
 */
public interface VoteService {

  void vote(long restaurantId, long userId) throws NotFoundException, VoteNotAcceptedException;

  void unVote(long userId) throws NotFoundException;

  List<Vote> getByDate(LocalDate date);
}
