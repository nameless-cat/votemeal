package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;
import static com.agromov.votemeal.util.UserUtils.prepareToSave;

/**
 * Created by A.Gromov on 22.05.2017.
 */

@Repository
public class UserRepositoryImpl
        implements UserRepository
{
    @Autowired
    private UserJpaRepository repository;

    @Autowired
    private VoteHistoryJpaRepository historyRepository;

    @Override
    public User save(User user)
    {
        return repository.save(prepareToSave(user));
    }

    @Override
    public User update(User updated) throws NotFoundException
    {
        try
        {
            return repository.save(prepareToSave(updated));
        } catch (EntityNotFoundException e)
        {
            throw new NotFoundException();
        }
    }

    @Override
    public int delete(long id) throws NotFoundException
    {
        try
        {
            return repository.delete(id);

        } catch (EntityNotFoundException e)
        {
            throw new NotFoundException();
        }
    }

    @Override
    public User get(long id) throws NotFoundException
    {
        try
        {
            return repository.findOne(id);
        } catch (EntityNotFoundException e)
        {
            throw new NotFoundException();
        }
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

    @Override
    public List<VoteHistory> getHistory(Long userId)
    {
        return historyRepository.findAllByUserIdOrderByDateDesc(userId);
    }

    @Override
    public void addToCurrentHistory(long userId, Restaurant restaurant, Vote vote)
    {
        historyRepository.save(new VoteHistory(currentDate(), restaurant, userId, vote));
    }

    @Override
    public void deleteFromCurrentHistory(Long userId)
    {
        historyRepository.delete(userId, currentDate());
    }

    @Override
    public User getByEmail(String email)
    {
        return repository.getByEmail(email);
    }
}
