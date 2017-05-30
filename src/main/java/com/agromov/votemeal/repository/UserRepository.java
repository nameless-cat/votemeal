package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;

import static com.agromov.votemeal.util.ClassUtils.NOT_IMPLEMENTED;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public interface UserRepository extends DataRepository<User>
{
    default int delete(int id)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }
}
