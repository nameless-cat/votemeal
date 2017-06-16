package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
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
    public Lunch get( long restaurantId, long id) throws EntityNotFoundException
    {
        return repository.findByRestaurantIdAndId(restaurantId, id );
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
    {
        Restaurant restaurant = restaurantJpaRepository.findOne(restaurantId);
        if (restaurant == null)
        {
            throw new EntityNotFoundException();
        }
        lunch.setRestaurant(restaurant);
        return repository.save(lunch);
    }
}
