package com.agromov.votemeal.web;

import com.agromov.votemeal.config.ProjectConstants;
import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.service.LunchService;
import com.agromov.votemeal.service.RestaurantService;
import com.agromov.votemeal.service.VoteService;
import com.agromov.votemeal.util.exception.BadArgumentException;
import com.agromov.votemeal.util.exception.NotFoundException;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static com.agromov.votemeal.config.ProjectProperties.VOTE_DEADLINE;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.util.ValidationUtils.checkForNew;
import static com.agromov.votemeal.util.ValidationUtils.checkIdConsistence;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by A.Gromov on 14.06.2017.
 */
@RestController
@RequestMapping(AdminController.ADMIN_URL)
public class AdminController {

  static final String ADMIN_URL = "/v1/admin/";

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private VoteService voteService;

  @Autowired
  private LunchService lunchService;

  @Autowired
  private ProjectConstants projectConstants;

  @GetMapping(value = "restaurants", produces = APPLICATION_JSON_UTF8_VALUE)
  public List<Restaurant> getRestaurants() {
    return restaurantService.getAll();
  }

  @GetMapping(value = "restaurants/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  public Restaurant getRestaurant(@PathVariable("id") Long id)
      throws NotFoundException {
    return restaurantService.get(id);
  }

  @PostMapping(value = "restaurants",
      consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant)
      throws BadArgumentException {
    checkForNew(restaurant);

    Restaurant saved = restaurantService.save(restaurant);

    URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(ADMIN_URL + "restaurants/{id}")
        .buildAndExpand(saved.getId()).toUri();

    return ResponseEntity.created(uriOfNewResource).body(saved);
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping(value = "restaurants/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
  public void updateRestaurant(@PathVariable long id, @Valid @RequestBody Restaurant restaurant)
      throws NotFoundException, BadArgumentException {
    checkIdConsistence(restaurant, id);

    restaurantService.update(restaurant);
  }

  @GetMapping(value = {"vote/{date}", "vote"}, produces = APPLICATION_JSON_UTF8_VALUE)
  public List<Vote> getVoteAtDate(
      @PathVariable(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
      throws BadArgumentException {

    if (date != null) {
      if (date.isAfter(currentDate())) {
        throw new BadArgumentException();
      }

      return voteService.getByDate(date);
    } else {
      return voteService.getByDate(currentDate());
    }
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping(value = "vote", consumes = APPLICATION_JSON_UTF8_VALUE)
  public void addSetToCurrentVote(@RequestBody Set<Long> restaurantIds)
      throws Exception {
    restaurantService.addToVote(restaurantIds);
  }

  @ResponseStatus(ACCEPTED)
  @DeleteMapping(value = "vote")
  public void removeSetFromCurrentVote(@RequestBody Set<Long> restaurantIds)
      throws NotFoundException {
    restaurantService.deleteFromVote(restaurantIds);
  }

  @ResponseStatus(ACCEPTED)
  @DeleteMapping(value = "vote/{id}")
  public void removeOneFromCurrentVote(@PathVariable("id") Long restaurantId)
      throws NotFoundException {

    HashSet<Long> singletonSet = new HashSet<>();
    singletonSet.add(restaurantId);
    restaurantService.deleteFromVote(singletonSet);
  }

  @ResponseStatus(CREATED)
  @PostMapping(value = "restaurants/{id}/lunches", consumes = APPLICATION_JSON_UTF8_VALUE)
  public void addLunchToRestaurantMenu(@PathVariable Long id, @Valid @RequestBody Lunch lunch)
      throws NotFoundException {
    checkForNew(lunch);

    lunchService.save(id, lunch);
  }

  @GetMapping(value = "restaurants/{id}/lunches", produces = APPLICATION_JSON_UTF8_VALUE)
  public List<Lunch> getLunches(@PathVariable Long id)
      throws NotFoundException {
    return lunchService.getAll(id);
  }

  @GetMapping(value = "restaurants/{id}/lunches/{lunchId}", produces = APPLICATION_JSON_UTF8_VALUE)
  public Lunch getLunch(@PathVariable Long id, @PathVariable Long lunchId)
      throws NotFoundException {
    return lunchService.get(id, lunchId);
  }

  @ResponseStatus(ACCEPTED)
  @PutMapping(value = "restaurants/{id}/lunches/{lunchId}", consumes = APPLICATION_JSON_UTF8_VALUE)
  public void putLunch(@PathVariable Long id, @PathVariable Long lunchId,
      @Valid @RequestBody Lunch lunch)
      throws NotFoundException, BadArgumentException {
    checkIdConsistence(lunch, lunchId);

    lunchService.update(id, lunch);
  }

  @DeleteMapping(value = "restaurants/{id}/lunches/{lunchId}")
  public void deleteLunch(@PathVariable Long id, @PathVariable Long lunchId)
      throws NotFoundException {
    lunchService.delete(id, lunchId);
  }

  @PostMapping(value = "deadline")
  public void changeDeadLine(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time) {
    projectConstants.setParam(VOTE_DEADLINE, time);
  }
}
