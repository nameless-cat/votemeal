package com.agromov.votemeal.web;

import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.repository.UserRepository;
import com.agromov.votemeal.repository.VoteRepository;
import com.agromov.votemeal.config.ProjectConstants;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.agromov.votemeal.RestaurantTestData.BENJAMIN;
import static com.agromov.votemeal.RestaurantTestData.CHOCO;
import static com.agromov.votemeal.RestaurantTestData.SUBWAY;
import static com.agromov.votemeal.UserTestData.MARIA_ID;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.web.VoteController.VOTE_URL;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by A.Gromov on 17.06.2017.
 */
public class VoteControllerTest
        extends AbstractControllerTest
{
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Test
    public void postVoteMustIncrementCounterOfVotesOfRestaurant() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + BENJAMIN.getId()))
                .andDo(print())
                .andExpect(status().isAccepted());

        assertEquals(1, voteRepository.get(currentDate(), BENJAMIN.getId()).getVotes());
    }

    @Transactional
    @Test
    public void postVoteMustRefreshVoteHistoryOfUser() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + SUBWAY.getId()))
                .andDo(print())
                .andExpect(status().isAccepted());

        assertEquals(1, IterableUtils.countMatches(
                userRepository.getHistory(MARIA_ID),
                vh -> vh.getDate().equals(currentDate())));
    }

    @Transactional
    @Test
    public void revoteOnTheSameRestaurantMustNotIncrementVotes() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + CHOCO.getId()))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

        assertEquals(2, voteRepository.getAll(currentDate())
                .stream()
                .filter(v -> v.getRestaurant().equals(CHOCO))
                .findFirst()
                .get()
                .getVotes());
    }

    @Transactional
    @Test
    public void revoteOnTheDifferentRestaurantMustDeletePreviousVote() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + BENJAMIN.getId()))
                .andDo(print())
                .andExpect(status().isAccepted());

        List<Vote> votes = voteRepository.getAll(currentDate());

        assertEquals(1, votes.stream()
                .filter(v -> v.getRestaurant().equals(CHOCO))
                .findFirst()
                .get()
                .getVotes());
    }

    @Transactional
    @Test
    public void deleteVoteMustRemoveCurrentVoteOfUser() throws Exception
    {
        mockMvc.perform(delete(VOTE_URL))
                .andDo(print())
                .andExpect(status().isAccepted());

        assertEquals(1, voteRepository.getAll(currentDate()).stream()
                .filter(v -> v.getRestaurant().equals(CHOCO))
                .findFirst()
                .get()
                .getVotes());
    }
}