package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Lunch;

import javax.persistence.EntityNotFoundException;

/**
 * Created by A.Gromov on 16.06.2017.
 */
public interface LunchService
{
    Lunch get(long restaurantId, long lunchId) throws EntityNotFoundException;

    Lunch save (long restaurantId, Lunch lunch) throws EntityNotFoundException;

    void update(long restaurantId, Lunch lunch) throws EntityNotFoundException;

    void delete(long restaurantId, long lunchId) throws EntityNotFoundException;
}
