package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by A.Gromov on 22.05.2017.
 */

@Repository
public class UserRepository implements DataRepository<User>
{
    private UserCrudRepository repository;

    @Autowired
    public UserRepository(UserCrudRepository repository)
    {
        this.repository = repository;
    }

    public List<User> getList(int offset, int limit)
    {
        return null;
    }

    public User save(User restaurant)
    {
        return null;
    }

    public int delete(int id)
    {
        return 0;
    }

    public User get(int id)
    {
        return repository.getOne(id);
    }
}
