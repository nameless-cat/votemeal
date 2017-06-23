package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.SimpleVote;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.util.JpaUtils;
import com.agromov.votemeal.util.exception.BadArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Repository
public class VoteRepositoryImpl
        implements VoteRepository
{
    @Autowired
    private VoteJpaRepository voteRepository;

    @Autowired
    private SimpleVoteRepository simpleRepository;

    @Autowired
    private JpaUtils jpaUtils;

    @Override
    public void addToVote(List<Long> restaurantIds) throws Exception
    {
        try
        {
            jpaUtils.batchInsert(SimpleVote.class, restaurantIds);
        } catch (IllegalAccessException | InstantiationException e)
        {
            throw new Exception();
        }
    }

    @Override
    public int removeFromVote(long restaurantId)
    {
        return simpleRepository.remove(restaurantId, currentDate());
    }

    @Override
    public boolean increment(long restaurantId)
    {
        return simpleRepository.increment(currentDate(), restaurantId) != 0;
    }

    @Override
    public boolean decrement(long restaurantId) throws BadArgumentException
    {
        SimpleVote vote = simpleRepository.findOne(new SimpleVote.SimpleVotePK(currentDate(), restaurantId));

        if (vote == null)
            return false;

        if (vote.getVotes() < 1)
            throw new BadArgumentException();

        vote.setVotes(vote.getVotes() - 1);

        return simpleRepository.save(vote) != null;
    }

    @Override
    public List<Vote> getAll(LocalDate date)
    {
        return voteRepository.findByDate(date)
                .stream()
                .sorted((o1, o2) -> (o1.getVotes() == o2.getVotes()) ?
                        o1.getRestaurant().getName().compareTo(o2.getRestaurant().getName())
                        : Integer.compare(o2.getVotes(), o1.getVotes()))
                .collect(Collectors.toList());
    }

    @Override
    public Vote get(LocalDate date, long restaurantId)
    {
        return voteRepository.find(date, restaurantId);
    }
}
