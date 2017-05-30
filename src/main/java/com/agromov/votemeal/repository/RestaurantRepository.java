package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;

import java.util.List;

import static com.agromov.votemeal.util.ClassUtils.NOT_IMPLEMENTED;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public interface RestaurantRepository
        extends DataRepository<Restaurant>
{
    default boolean close(int id)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default boolean open(int id)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<Restaurant> getAllOpened()
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
