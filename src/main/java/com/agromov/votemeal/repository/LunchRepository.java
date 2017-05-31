package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Lunch;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.agromov.votemeal.util.ClassUtils.NOT_IMPLEMENTED;

/**
 * Created by A.Gromov on 31.05.2017.
 */
public interface LunchRepository
{
    default int delete(int id, int restaurantId)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<Lunch> getRange(int offset, int limit, int restaurantId)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default Lunch save(Lunch object)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    //todo убрать ненужные выбросы EntityNotFoundException, т.к. если не найдено, то вернет "0 rows affected"


    default Lunch get(int id, int restaurantId) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<Lunch> getAll(int restaurantId)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
