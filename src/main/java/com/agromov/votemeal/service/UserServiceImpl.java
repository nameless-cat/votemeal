package com.agromov.votemeal.service;

import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.repository.UserRepository;
import com.agromov.votemeal.web.Authorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.agromov.votemeal.util.UserUtils.prepareToSave;

/**
 * Created by A.Gromov on 12.06.2017.
 */
@Service("userService")
public class UserServiceImpl
        implements UserService, UserDetailsService
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

    @Override
    public Authorized loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = repository.getByEmail(email);
        if (user == null)
        {
            throw new UsernameNotFoundException("User with email " + email + " not present.");
        }

        return new Authorized(user);
    }
}
