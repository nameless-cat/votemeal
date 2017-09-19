package com.agromov.votemeal.web;

import com.agromov.votemeal.config.ProjectConstants;
import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.service.LunchService;
import com.agromov.votemeal.service.RestaurantService;
import com.agromov.votemeal.service.VoteService;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.agromov.votemeal.config.LocalizationCodes.TIME_IS_UP;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.util.DateTimeUtil.currentTime;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by A.Gromov on 17.06.2017.
 */
@RestController
@RequestMapping(value = VoteController.VOTE_URL)
public class VoteController {

  public static final String VOTE_URL = "/v1/vote";

  @Autowired
  private VoteService voteService;

  @Autowired
  private LunchService lunchService;

  @Autowired
  private ProjectConstants projectConstants;

  @Autowired
  private RestaurantService restaurantService;

  @ResponseStatus(ACCEPTED)
  @PostMapping(value = "/{id}")
  public void vote(@PathVariable("id") Long restaurantId)
      throws NotFoundException, VoteNotAcceptedException {
    maybeTimeIsUp();
    voteService.vote(restaurantId, Authorized.getUser().getId());
  }

  @ResponseStatus(ACCEPTED)
  @DeleteMapping
  public void deleteVote() throws VoteNotAcceptedException {
    maybeTimeIsUp();
    voteService.unVote(Authorized.getUser().getId());
  }

  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  public List<Restaurant> getVote() {
    return restaurantService.getVoteList();
  }

  @GetMapping(value = "/restaurant/{id}/lunches", produces = APPLICATION_JSON_UTF8_VALUE)
  public List<Lunch> getLunches(@PathVariable Long id)
      throws NotFoundException {
    return lunchService.getAll(id);
  }

  private void maybeTimeIsUp() throws VoteNotAcceptedException {
    if (currentTime().isAfter(projectConstants.getVoteDeadline())) {
      throw new VoteNotAcceptedException(TIME_IS_UP);
    }
  }
}