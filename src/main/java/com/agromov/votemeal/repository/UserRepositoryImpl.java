package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by A.Gromov on 22.05.2017.
 */

@Repository
public class UserRepositoryImpl
        implements UserRepository
{
    @Autowired
    private UserCrudRepository repository;

    @Override
    public User save(User user)
    {
        return repository.save(user);
    }

    @Override
    public User update(User updated) throws EntityNotFoundException
    {
        // todo см комментарии в UserRepositoryTest#getAndModifyUserAndPersistMustReflectThisOnDB()
        return repository.save(updated);
    }

    @Override
    public int delete(int id) throws EntityNotFoundException
    {
        return repository.delete(id);
    }

    @Override
    public User get(int id) throws EntityNotFoundException
    {
        return repository.findOne(id);
    }

    @Override
    public List<User> getAll()
    {
        return repository.findAllByOrderByName();
    }

    @Override
    public List<User> getRange(int offset, int limit)
    {
        return repository.findAllByOrderByName(new PageRequest(offset, limit));
    }
}
