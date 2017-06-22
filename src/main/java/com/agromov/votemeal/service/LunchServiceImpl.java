package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.repository.LunchRepository;
import com.agromov.votemeal.repository.RestaurantRepository;
import com.agromov.votemeal.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by A.Gromov on 16.06.2017.
 */
@Service
public class LunchServiceImpl
        implements LunchService
{
    @Autowired
    private LunchRepository lunchRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Lunch get(long restaurantId, long lunchId) throws NotFoundException
    {
        Lunch lunch = lunchRepository.get(restaurantId, lunchId);

        if (lunch == null)
        {
            throw new NotFoundException();
        }

        return lunch;
    }

    @Override
    public Lunch save(long restaurantId, Lunch lunch) throws NotFoundException
    {
        Lunch saved = lunchRepository.save(restaurantId, lunch);

        if (saved == null)
            throw new NotFoundException();

        return saved;
    }

    @Override
    public void delete(long restaurantId, long lunchId) throws NotFoundException
    {
        if (lunchRepository.delete(restaurantId, lunchId) == 0)
        {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Lunch> getAll(long restaurantId) throws NotFoundException
    {
        if (restaurantRepository.get(restaurantId) == null)
        {
            throw new NotFoundException();
        }

        return lunchRepository.getAll(restaurantId);
    }

    @Override
    public void update(long restaurantId, Lunch lunch) throws NotFoundException
    {
        Objects.requireNonNull(lunch.getId());
        get(restaurantId, lunch.getId());
        lunchRepository.save(restaurantId, lunch);
    }
}
