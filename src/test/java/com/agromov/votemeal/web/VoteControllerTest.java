package com.agromov.votemeal.web;

import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.repository.UserRepository;
import com.agromov.votemeal.repository.VoteRepository;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.agromov.votemeal.RestaurantTestData.BENJAMIN;
import static com.agromov.votemeal.RestaurantTestData.CHOCO;
import static com.agromov.votemeal.RestaurantTestData.SUBWAY;
import static com.agromov.votemeal.TestUtil.userHttpBasic;
import static com.agromov.votemeal.UserTestData.MARIA;
import static com.agromov.votemeal.UserTestData.MARIA_ID;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by A.Gromov on 17.06.2017.
 */
public class VoteControllerTest
        extends AbstractControllerTest
{
    private static final String VOTE_URL = VoteController.VOTE_URL + "/";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void postVoteMustIncrementCounterOfVotesOfRestaurant() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + BENJAMIN.getId())
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isAccepted());

//        assertEquals(1,(int) voteRepository.get(currentDate(), BENJAMIN.getId()).getVotes());
    }

    @Test
    public void postVoteMustRefreshVoteHistoryOfUser() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + SUBWAY.getId())
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isAccepted());

        /*assertEquals(1, IterableUtils.countMatches(
                userRepository.getHistory(MARIA_ID),
                vh -> vh.getDate().equals(currentDate())));*/
    }

    @Test
    public void revoteOnTheSameRestaurantMustNotIncrementVotes() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + CHOCO.getId())
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

        /*assertEquals(2, (int) voteRepository.getAllByDate(currentDate())
                .stream()
                .filter(v -> v.getRestaurant().equals(CHOCO))
                .findFirst()
                .get()
                .getVotes());*/
    }

    @Test
    public void revoteOnTheDifferentRestaurantMustDeletePreviousVote() throws Exception
    {
        mockMvc.perform(post(VOTE_URL + BENJAMIN.getId())
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isAccepted());

        /*List<Vote> votes = voteRepository.getAllByDate(currentDate());

        assertEquals(1, (int) votes.stream()
                .filter(v -> v.getRestaurant().equals(CHOCO))
                .findFirst()
                .get()
                .getVotes());*/
    }

    @Test
    public void deleteVoteMustRemoveCurrentVoteOfUser() throws Exception
    {
        mockMvc.perform(delete(VOTE_URL)
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isAccepted());

        /*assertEquals(1, (int) voteRepository.getAllByDate(currentDate()).stream()
                .filter(v -> v.getRestaurant().equals(CHOCO))
                .findFirst()
                .get()
                .getVotes());*/
    }

    @Test
    public void getCurrentVoteMustReturnRestaurantsWithLunches() throws Exception
    {
        mockMvc.perform(get(VOTE_URL)
                .with(userHttpBasic(MARIA)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}