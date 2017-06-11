package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Repository
public class RestaurantRepositoryImpl
    implements RestaurantRepository
{
    @Autowired
    private RestaurantJpaRepository repository;

    @Override
    public Restaurant get(long id) throws EntityNotFoundException
    {
        return repository.findOne(id);
    }

    @Override
    public List<Restaurant> getAll()
    {
        return repository.findAllByOrderByName();
    }

    @Override
    public boolean close(long id)
    {
        return repository.close(id) != 0;
    }

    @Override
    public boolean open(long id)
    {
        return repository.open(id) != 0;
    }

    @Override
    public List<Restaurant> getAllOpened()
    {
        return repository.findAllByClosedIsFalseOrderByName();
    }

    @Override
    public Restaurant save(Restaurant restaurant)
    {
        return repository.save(restaurant);
    }

    @Override
    public List<Restaurant> getRange(int offset, int limit)
    {
        return repository.findAllByOrderByName(new PageRequest(offset, limit));
    }
}
