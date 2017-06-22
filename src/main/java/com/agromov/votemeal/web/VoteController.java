package com.agromov.votemeal.web;

import com.agromov.votemeal.config.ProjectConstants;
import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.service.LunchService;
import com.agromov.votemeal.service.VoteService;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.util.DateTimeUtil.currentTime;

/**
 * Created by A.Gromov on 17.06.2017.
 */
@RestController
@RequestMapping(value = VoteController.VOTE_URL)
public class VoteController
{
    public static final String VOTE_URL = "/v1/vote";

    @Autowired
    private VoteService voteService;

    @Autowired
    private LunchService lunchService;

    @Autowired
    private ProjectConstants projectConstants;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void incrementVote(@PathVariable Long id)
            throws EntityNotFoundException, VoteNotAcceptedException
    {
        maybeTimeIsUp();
        voteService.increment(id, Authorized.getUser().getId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void incrementVote() throws VoteNotAcceptedException
    {
        maybeTimeIsUp();
        voteService.decrement(Authorized.getUser().getId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Vote> getVote()
    {
        return voteService.get(currentDate());
    }

    @GetMapping(value = "/restaurant/{id}/lunches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Lunch> getLunches(@PathVariable Long id)
            throws EntityNotFoundException
    {
        return lunchService.getAll(id);
    }

    private void maybeTimeIsUp() throws VoteNotAcceptedException
    {
        if (currentTime().isAfter(projectConstants.getVoteDeadline()))

            //todo need i18n
            throw new VoteNotAcceptedException(/*"time is up"*/);

    }
}