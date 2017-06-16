package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.repository.LunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

/**
 * Created by A.Gromov on 16.06.2017.
 */
@Service
public class LunchServiceImpl
        implements LunchService
{
    @Autowired
    private LunchRepository repository;

    @Override
    public Lunch get(long restaurantId, long lunchId) throws EntityNotFoundException
    {
        Lunch lunch = repository.get(restaurantId, lunchId);

        if (lunch == null)
        {
            throw new EntityNotFoundException();
        }

        return lunch;
    }

    @Override
    public Lunch save(long restaurantId, Lunch lunch) throws EntityNotFoundException
    {
        Lunch saved = repository.save(restaurantId, lunch);

        if (saved == null)
            throw new EntityNotFoundException();

        return saved;
    }

    @Override
    public void delete(long restaurantId, long lunchId) throws EntityNotFoundException
    {
        if (repository.delete(restaurantId, lunchId) == 0)
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void update(long restaurantId, Lunch lunch) throws EntityNotFoundException
    {
        Objects.requireNonNull(lunch.getId());
        get(restaurantId, lunch.getId());
        repository.save(restaurantId, lunch);
    }
}
