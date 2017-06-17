package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import java.util.List;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public interface RestaurantRepository
{
    List<Restaurant> getRange(int offset, int limit);

    Restaurant save(Restaurant object);

    Restaurant get(long id);

    List<Restaurant> getAll();

    Restaurant getWithLunches(Long id);

    Restaurant testGet(long id);
}