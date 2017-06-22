package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by A.Gromov on 31.05.2017.
 */
@Repository
public class LunchRepositoryImpl
    implements LunchRepository
{
    @Autowired
    private LunchJpaRepository repository;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public Lunch get( long restaurantId, long id) throws NotFoundException
    {
        try
        {
            return repository.findByRestaurantIdAndId(restaurantId, id );
        } catch (EntityNotFoundException e)
        {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Lunch> getAll(long restaurantId)
    {
        return repository.findAllByRestaurantIdOrderByName(restaurantId);
    }

    @Override
    public int delete(long restaurantId, long id)
    {
        return repository.delete(restaurantId, id);
    }

    @Transactional
    @Override
    public Lunch save(long restaurantId, Lunch lunch)
            throws NotFoundException
    {
        Restaurant restaurant = restaurantJpaRepository.findOne(restaurantId);
        if (restaurant == null)
        {
            throw new NotFoundException();
        }
        lunch.setRestaurant(restaurant);
        return repository.save(lunch);
    }
}
