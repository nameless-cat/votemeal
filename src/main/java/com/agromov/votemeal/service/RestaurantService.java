package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by A.Gromov on 14.06.2017.
 */
public interface RestaurantService
{
    List<Restaurant> getAll();

    Restaurant get(Long id) throws EntityNotFoundException;

    Restaurant save(Restaurant restaurant);

    Restaurant update(Restaurant restaurant) throws EntityNotFoundException;

    void addLunch(Long id, Lunch lunch) throws EntityNotFoundException;
}
