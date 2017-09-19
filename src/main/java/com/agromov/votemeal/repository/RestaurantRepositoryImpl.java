package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.util.exception.NotFoundException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Repository
public class RestaurantRepositoryImpl
    implements RestaurantRepository {

  @Autowired
  private RestaurantJpaRepository repository;

  @Override
  public Restaurant get(long id) {
    return repository.findOne(id);
  }

  @Override
  public List<Restaurant> getAll() {
    return repository.findAllByOrderByName();
  }

  @Override
  public Restaurant save(Restaurant restaurant) {
    return repository.save(restaurant);
  }

  @Override
  public List<Restaurant> getRange(int offset, int limit) {
    return repository.findAllByOrderByName(new PageRequest(offset, limit));
  }

  @Override
  public Restaurant getWithLunches(Long id) {
    return repository.findOneWithLunches(id);
  }

  @Override
  public void addToVote(Set<Long> restaurantIds) throws Exception {
    setVoteFlag(restaurantIds, true);
  }

  @Override
  public void removeFromVote(Set<Long> restaurantIds) {
    setVoteFlag(restaurantIds, false);
  }

  @Override
  public List<Restaurant> getForVote() {
    return getAll().stream()
        .filter(Restaurant::isForVote)
        .collect(Collectors.toList());
  }

  private void setVoteFlag(Set<Long> restaurantIds, boolean flag) {
    List<Restaurant> restaurants = getAll();

    if (!isRestaurantsExisted(restaurantIds, restaurants)) {
      throw new NotFoundException(/*todo рестран не найден*/);
    }

    restaurants.stream()
        .filter(restaurant -> restaurantIds.contains(restaurant.getId()))
        .collect(Collectors.toList())
        .forEach(restaurant -> restaurant.setForVote(flag));
  }

  private boolean isRestaurantsExisted(Set<Long> restaurantIds, List<Restaurant> restaurants) {
    for (Long id : restaurantIds) {
      if (restaurants.stream().noneMatch(r -> r.getId().equals(id))) {
        return false;
      }
    }
    return true;
  }
}