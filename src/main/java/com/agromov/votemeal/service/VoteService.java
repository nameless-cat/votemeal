package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 14.06.2017.
 */
public interface VoteService {

  void vote(long restaurantId, long userId) throws NotFoundException, VoteNotAcceptedException;

  void unVote(long userId) throws NotFoundException;

  List<Restaurant> getVote();

  List<Restaurant> getVote(LocalDate date);
}
