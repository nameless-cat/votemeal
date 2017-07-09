package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
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
    private JpaUtils jpaUtils;

    @Override
    public void addToVote(List<Restaurant> restaurants) throws Exception
    {
        try
        {
            jpaUtils.batchInsert(Vote.class, restaurants);
        } catch (IllegalAccessException | InstantiationException e)
        {
            throw new Exception();
        }
    }

    @Override
    public int removeFromVote(long restaurantId)
    {
        return voteRepository.remove(restaurantId, currentDate());
    }

    @Override
    public boolean increment(long restaurantId)
    {
        return voteRepository.increment(currentDate(), restaurantId) != 0;
    }

    @Override
    public boolean decrement(long restaurantId) throws BadArgumentException
    {
        Vote vote = voteRepository.find(currentDate(), restaurantId);

        if (vote == null)
            return false;

        if (vote.getVotes() < 1)
            throw new BadArgumentException();

        vote.setVotes(vote.getVotes() - 1);

        return voteRepository.save(vote) != null;
    }

    @Override
    public List<Vote> getAll(LocalDate date)
    {
        return voteRepository.findByDate(date)
                .stream()
                .sorted((o1, o2) -> (o1.getVotes().equals(o2.getVotes())) ?
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
