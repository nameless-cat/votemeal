package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.repository.RestaurantRepository;
import com.agromov.votemeal.repository.UserRepository;
import com.agromov.votemeal.repository.VoteRepository;
import com.agromov.votemeal.util.exception.BadArgumentException;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.agromov.votemeal.config.LocalizationCodes.VOTED_ALREADY;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 14.06.2017.
 */
@Service
public class VoteServiceImpl
    implements VoteService {

  @Autowired
  private VoteRepository voteRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Transactional
  @Override
  public void vote(long restaurantId, long userId)
      throws NotFoundException, VoteNotAcceptedException {

    Restaurant restaurant = restaurantRepository.get(restaurantId);

    if (restaurant == null) {
      throw new NotFoundException(/*todo сообщение о ненайденном ресторане*/);
    }

    VoteHistory todayVote = getTodayVote(userId);

    if (todayVote == null) {

      todayVote = new VoteHistory(currentDate(), restaurant, userId);
      voteRepository.save(todayVote);

    } else {
      todayVote.setRestaurant(restaurant);
    }
  }

  @Override
  public void unVote(long userId) throws NotFoundException {
    if (voteRepository.delete(userId) == 0) {
      throw new NotFoundException(/*todo сообщение о ненайденном голосовании*/);
    }
  }

  @Override
  public List<Vote> getByDate(LocalDate date) {
    return voteRepository.getHistory(date).stream()
        .collect(Collectors.groupingBy(VoteHistory::getRestaurant))
        .entrySet().stream()
        .map(entry -> new Vote(date, entry.getKey(), entry.getValue().size()))
        .collect(Collectors.toList());
  }

  private VoteHistory getTodayVote(long userId) {
    return voteRepository.getUserHistory(userId)
        .stream()
        .filter(vh -> vh.getDate().equals(currentDate()))
        .findAny()
        .orElse(null);
  }
}
