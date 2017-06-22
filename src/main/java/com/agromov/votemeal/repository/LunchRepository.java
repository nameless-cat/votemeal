package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Lunch;

import java.util.List;

/**
 * Created by A.Gromov on 31.05.2017.
 */
public interface LunchRepository
{
    int delete(long restaurantId, long id);

    Lunch save(long restaurantId, Lunch object);

    Lunch get(long restaurantId, long id);

    List<Lunch> getAll(long restaurantId);
}
