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
    public Restaurant get(long id)
    {
        return repository.findOne(id);
    }

    @Override
    public List<Restaurant> getAll()
    {
        return repository.findAllByOrderByName();
    }

    @Override
    public Restaurant save(Restaurant restaurant)
    {
        //todo Assert.notNull проверять все сущности перед сохранением во всех репозиториях
        return repository.save(restaurant);
    }

    @Override
    public List<Restaurant> getRange(int offset, int limit)
    {
        return repository.findAllByOrderByName(new PageRequest(offset, limit));
    }

    @Override
    public Restaurant getWithLunches(Long id)
    {
        return repository.findOneWithLunches(id);
    }

    @Override
    public Restaurant testGet(long id)
    {
        return repository.findById(id);
    }
}