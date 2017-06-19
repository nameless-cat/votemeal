package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.repository.LunchRepository;
import com.agromov.votemeal.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

/**
 * Created by A.Gromov on 14.06.2017.
 */
@Service
public class RestaurantServiceImpl
        implements RestaurantService
{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private LunchRepository lunchRepository;

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll()
    {
        return restaurantRepository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant save(Restaurant restaurant)
    {
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant update(Restaurant restaurant) throws EntityNotFoundException
    {
        Objects.requireNonNull(restaurant.getId());
        get(restaurant.getId());
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @Override
    public void addLunch(Long id, Lunch lunch) throws EntityNotFoundException
    {
        if (lunchRepository.save(id, lunch) == null)
        {
            throw new EntityNotFoundException();
        }
    }

    @Cacheable("restaurants")
    @Override
    public Restaurant get(Long id) throws EntityNotFoundException
    {
        Restaurant restaurant = restaurantRepository.getWithLunches(id);

        if (restaurant == null)
            throw new EntityNotFoundException();

        return restaurant;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void cacheEvict() {}
}
