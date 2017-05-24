package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;

import java.util.List;

/**
 * Created by A.Gromov on 22.05.2017.
 */
public interface DataRepository<T>
{
    List<T> getList(int offset, int limit);

    T save(T restaurant);

    int delete(int id);

    T get(int id);
}
