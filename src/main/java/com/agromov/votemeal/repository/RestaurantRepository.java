package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.agromov.votemeal.util.ClassUtils.NOT_IMPLEMENTED;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public interface RestaurantRepository
{
    default List<Restaurant> getRange(int offset, int limit)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default Restaurant save(Restaurant object)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    //todo убрать ненужные выбросы EntityNotFoundException, т.к. если не найдено, то вернет "0 rows affected"


    default Restaurant get(long id) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<Restaurant> getAll()
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default Restaurant update(Restaurant updated) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default boolean close(long id)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default boolean open(long id)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<Restaurant> getAllOpened()
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
