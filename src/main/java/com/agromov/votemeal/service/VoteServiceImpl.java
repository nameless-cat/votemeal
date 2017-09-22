package com.agromov.votemeal.service;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.repository.RestaurantRepository;
import com.agromov.votemeal.repository.VoteRepository;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      voteRepository.update(todayVote);
    }

  }

  @Override
  public void unVote(long userId) throws NotFoundException {
    if (voteRepository.delete(userId) == 0) {
      throw new NotFoundException(/*todo сообщение о ненайденном голосовании*/);
    }
  }

  @Override
  public List<Restaurant> getVote(LocalDate date) {
    return voteRepository.getHistory(date).stream()
        .collect(Collectors.groupingBy(VoteHistory::getRestaurant, Collectors.toList()))
        .entrySet().stream()
        .map(Entry::getKey)
        .sorted(Comparator.comparing(Restaurant::getName))
        .collect(Collectors.toList());
  }

  @Override
  public List<Restaurant> getVote() {
    return restaurantRepository.getForVote();
  }

  private VoteHistory getTodayVote(long userId) {
    return voteRepository.getUserHistory(userId)
        .stream()
        .filter(vh -> vh.getDate().equals(currentDate()))
        .findAny()
        .orElse(null);
  }
}
