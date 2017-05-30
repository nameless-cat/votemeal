package com.agromov.votemeal.repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.agromov.votemeal.util.ClassUtils.NOT_IMPLEMENTED;

/**
 * Created by A.Gromov on 22.05.2017.
 */
public interface DataRepository<T>
{
    default List<T> getRange(int offset, int limit)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default T save(T object)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    //todo убрать ненужные выбросы EntityNotFoundException, т.к. если не найдено, то вернет "0 rows affected"


    default T get(int id) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<T> getAll()
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default T update(T updated) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
