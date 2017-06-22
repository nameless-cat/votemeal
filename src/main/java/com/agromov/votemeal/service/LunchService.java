package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by A.Gromov on 16.06.2017.
 */
public interface LunchService
{
    Lunch get(long restaurantId, long lunchId) throws NotFoundException;

    Lunch save (long restaurantId, Lunch lunch) throws NotFoundException;

    void update(long restaurantId, Lunch lunch) throws NotFoundException;

    void delete(long restaurantId, long lunchId) throws NotFoundException;

    List<Lunch> getAll(long restaurantId) throws NotFoundException;
}
