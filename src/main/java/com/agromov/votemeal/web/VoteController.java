package com.agromov.votemeal.web;

import com.agromov.votemeal.service.VoteService;
import com.agromov.votemeal.util.Authorized;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;

import static com.agromov.votemeal.config.ProjectConstants.VOTE_DEADLINE;

/**
 * Created by A.Gromov on 17.06.2017.
 */
@RestController
@RequestMapping(value = VoteController.VOTE_URL)
public class VoteController
{
    public static final String VOTE_URL = "/vote/";

    @Autowired
    private VoteService service;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void incrementVote(@PathVariable Long id)
            throws EntityNotFoundException, VoteNotAcceptedException
    {
        maybeTimeIsUp();
        service.increment(id, Authorized.getId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void incrementVote() throws VoteNotAcceptedException
    {
        maybeTimeIsUp();
        service.decrement(Authorized.getId());
    }

    private void maybeTimeIsUp() throws VoteNotAcceptedException
    {
        if (LocalTime.now().isAfter(VOTE_DEADLINE))

            //todo need i18n
            throw new VoteNotAcceptedException(/*"time is up"*/);

    }
}