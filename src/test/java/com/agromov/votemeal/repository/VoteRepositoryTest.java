package com.agromov.votemeal.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.agromov.votemeal.RestaurantTestData.*;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.web.VoteTestData.*;
import static com.agromov.votemeal.web.VoteTestData.MATCHER;
import static org.junit.Assert.*;

/**
 * Created by A.Gromov on 07.06.2017.
 */
public class VoteRepositoryTest
        extends AbstractRepositoryTest
{
    @Autowired
    private VoteRepository repository;

    @Test
    public void addRestaurantToVoteMustReflectChangesInDB() throws Exception
    {
        repository.addToVote(MCDONALDS.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(CHOCO_VOTE, SUBWAY_VOTE, BENJAMIN_VOTE, MCDONALDS_VOTE), repository.getAll(currentDate()));
        // todo слишком много запросов в базу, BatchSize в Vote не помог
    }

    @Test
    public void removeRestaurantFromVoteMustReflectChangesInDB() throws Exception
    {
        repository.removeFromVote(SUBWAY.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(CHOCO_VOTE, BENJAMIN_VOTE), repository.getAll(currentDate()));
    }

    @Test
    public void incrementVoteMustReflectChangesInDB() throws Exception
    {
        repository.increment(SUBWAY.getId());
        assertEquals(13, repository.get(currentDate(), SUBWAY.getId()).getVotes());
    }

    @Test
    public void incrementVoteOfCafeThatNotInVoteForTodayMustReturnFalse() throws Exception
    {
        assertFalse(repository.increment(MCDONALDS.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void decrementVoteOfZeroMustThrowException() throws Exception
    {
        repository.decrement(BENJAMIN.getId());
    }

    @Test
    public void decrementVoteMustREflectChangesInDB() throws Exception
    {
        repository.decrement(SUBWAY.getId());
        assertEquals(11, repository.get(currentDate(), SUBWAY.getId()).getVotes());
    }

    @Test
    public void decrementVoteOfCafeThatNotInVoteForTodayMustReturnFalse() throws Exception
    {
        assertFalse(repository.decrement(MCDONALDS.getId()));
    }
}
