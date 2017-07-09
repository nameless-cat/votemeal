package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.repository.RestaurantRepository;
import com.agromov.votemeal.repository.UserRepository;
import com.agromov.votemeal.repository.VoteRepository;
import com.agromov.votemeal.util.exception.BadArgumentException;
import com.agromov.votemeal.util.exception.NotFoundException;
import com.agromov.votemeal.util.exception.VoteNotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.agromov.votemeal.config.LocalizationCodes.NOT_VOTED_YET;
import static com.agromov.votemeal.config.LocalizationCodes.VOTED_ALREADY;
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
    public void add(Set<Long> restaurantIds)
            throws Exception
    {
        List<Restaurant> all = restaurantRepository.getAll();

        if (!isRestaurantsExisted(restaurantIds, all))
        {
            throw new BadArgumentException();
        }

        voteRepository.addToVote(all.stream()
                .filter(restaurant -> restaurantIds.contains(restaurant.getId()))
                .collect(Collectors.toList()));
    }

    private boolean isRestaurantsExisted(Set<Long> restaurantIds, List<Restaurant> restaurants)
    {
        for (Long id : restaurantIds)
        {
            if (restaurants.stream().noneMatch(r -> r.getId().equals(id)))
            {
                return false;
            }
        }
        return true;
    }

    @Transactional
    @Override
    public void increment(long restaurantId, long userId)
            throws NotFoundException, VoteNotAcceptedException
    {
        VoteHistory todayVoteHistory = getTodayVote(userId);

        if (todayVoteHistory != null)
        {
            if (todayVoteHistory.getRestaurant().getId().equals(restaurantId))
            {
                throw new VoteNotAcceptedException(VOTED_ALREADY);
            }

            voteRepository.decrement(todayVoteHistory.getRestaurant().getId());
            userRepository.deleteFromCurrentHistory(userId);
        }

        voteRepository.increment(restaurantId);
        userRepository.addToCurrentHistory(userId, restaurantRepository.get(restaurantId), voteRepository.get(currentDate(), restaurantId));
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
            userRepository.deleteFromCurrentHistory(userId);
        } else
            throw new VoteNotAcceptedException(NOT_VOTED_YET);
    }

    @Override
    public void delete(long restaurantId) throws NotFoundException
    {
        if (voteRepository.removeFromVote(restaurantId) == 0)
        {
            throw new NotFoundException();
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
