package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Lunch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public Lunch get(int id, int restaurantId) throws EntityNotFoundException
    {
        return repository.findByIdAndRestaurantId(id, restaurantId);
    }

    @Override
    public List<Lunch> getAll(int restaurantId)
    {
        return repository.findAllByRestaurantIdOrderByName(restaurantId);
    }

    @Override
    public int delete(int id, int restaurantId)
    {
        return repository.delete(id, restaurantId);
    }

    @Override
    public Lunch save(Lunch lunch)
    {
        return repository.save(lunch);
    }
}
