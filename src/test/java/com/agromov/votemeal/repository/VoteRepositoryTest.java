package com.agromov.votemeal.repository;

import com.agromov.votemeal.util.exception.BadArgumentException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static com.agromov.votemeal.RestaurantTestData.*;
import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.web.VoteTestData.*;
import static com.agromov.votemeal.web.VoteTestData.MATCHER;
import static org.junit.Assert.*;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Transactional
public class VoteRepositoryTest
        extends AbstractRepositoryTest
{
    @Autowired
    private VoteRepository repository;

    @Test
    public void addRestaurantToVoteMustReflectChangesInDB() throws Exception
    {
        repository.addToVote(Collections.singletonList(MCDONALDS_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(CHOCO_VOTE, SUBWAY_VOTE, BENJAMIN_VOTE, MCDONALDS_VOTE), repository.getAll(currentDate()));
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
        assertEquals(2, repository.get(currentDate(), SUBWAY.getId()).getVotes());
    }

    @Test
    public void incrementVoteOfCafeThatNotInVoteForTodayMustReturnFalse() throws Exception
    {
        assertFalse(repository.increment(MCDONALDS.getId()));
    }

    @Test(expected = BadArgumentException.class)
    public void decrementVoteOfZeroMustThrowException() throws Exception
    {
        repository.decrement(BENJAMIN.getId());
    }

    @Test
    public void decrementVoteMustReflectChangesInDB() throws Exception
    {
        repository.decrement(SUBWAY.getId());
        assertEquals(0, repository.get(currentDate(), SUBWAY.getId()).getVotes());
    }

    @Test
    public void decrementVoteOfCafeThatNotInVoteForTodayMustReturnFalse() throws Exception
    {
        assertFalse(repository.decrement(MCDONALDS.getId()));
    }
}
