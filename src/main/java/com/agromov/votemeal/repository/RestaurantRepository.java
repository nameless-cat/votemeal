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
    List<Restaurant> getRange(int offset, int limit);

    Restaurant save(Restaurant object);

    //todo убрать ненужные выбросы EntityNotFoundException, т.к. если не найдено, то вернет "0 rows affected"


    Restaurant get(long id);

    List<Restaurant> getAll();

    List<Restaurant> getAllOpened();

    Restaurant getWithLunches(Long id);

    Restaurant testGet(long id);
}
