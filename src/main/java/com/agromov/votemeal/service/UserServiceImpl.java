package com.agromov.votemeal.service;

import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by A.Gromov on 12.06.2017.
 */
@Service
public class UserServiceImpl
        implements UserService
{
    @Autowired
    private UserRepository repository;

    @Override
    public User get(Long id)
    {
        return repository.get(id);
    }

    @Override
    public User save(User user)
    {
        return repository.save(user);
    }

    @Override
    public User update(User user)
    {
        return repository.update(user);
    }

    @Override
    public List<VoteHistory> getHistory(Long id)
    {
        return repository.getHistory(id);
    }
}
