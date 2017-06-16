package com.agromov.votemeal.repository;


import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.agromov.votemeal.util.ClassUtils.NOT_IMPLEMENTED;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public interface UserRepository
{
    default int delete(long id)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<User> getRange(int offset, int limit)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default User save(User object)
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    //todo убрать ненужные выбросы EntityNotFoundException, т.к. если не найдено, то вернет "0 rows affected"


    default User get(long id) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default List<User> getAll()
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    default User update(User updated) throws EntityNotFoundException
    {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    List<VoteHistory> getHistory(Long id);
}
