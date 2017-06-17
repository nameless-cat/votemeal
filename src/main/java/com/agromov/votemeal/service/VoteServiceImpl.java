package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.repository.RestaurantRepository;
import com.agromov.votemeal.repository.UserRepository;
import com.agromov.votemeal.repository.VoteRepository;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 14.06.2017.
 */
@Service
public class VoteServiceImpl
        implements VoteService
{
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Vote> get(LocalDate date)
    {
        return voteRepository.getAll(date);
    }

    @Transactional
    @Override
    public void add(List<Long> restaurantIds)
    {
        // todo может можно сделать это одной операцией за одно обращение к базе?
        restaurantIds.forEach(voteRepository::addToVote);
    }

    @Transactional
    @Override
    public void increment(long restaurantId, long userId)
            throws EntityNotFoundException, VoteNotAcceptedException
    {
        VoteHistory todayVoteHistory = getTodayVote(userId);

        if (todayVoteHistory != null)
        {
            if (todayVoteHistory.getRestaurant().getId().equals(restaurantId))
            {
                //todo i18n
                throw new VoteNotAcceptedException(/*"already voted"*/);
            }

            voteRepository.decrement(todayVoteHistory.getRestaurant().getId());
        }

        voteRepository.increment(restaurantId);
        userRepository.refreshHistory(userId, restaurantRepository.get(restaurantId));
    }

    @Transactional
    @Override
    public void decrement(Long userId) throws VoteNotAcceptedException
    {
        VoteHistory todayVoteHistory = getTodayVote(userId);

        if (todayVoteHistory != null)
        {
            Restaurant restaurant = todayVoteHistory.getRestaurant();
            voteRepository.decrement(restaurant.getId());
            userRepository.refreshHistory(userId, null);
        } else
            throw new VoteNotAcceptedException(/*not voted yet for today*/);
    }

    @Override
    public void delete(long restaurantId) throws EntityNotFoundException
    {
        if (voteRepository.removeFromVote(restaurantId) == 0)
        {
            throw new EntityNotFoundException();
        }
    }

    private VoteHistory getTodayVote(long userId)
    {
        return userRepository.getHistory(userId)
                .stream()
                .filter(vh -> vh.getDate().equals(currentDate()))
                .findAny()
                .orElse(null);
    }
}
