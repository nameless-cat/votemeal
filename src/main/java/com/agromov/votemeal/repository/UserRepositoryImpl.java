package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;
import com.agromov.votemeal.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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
        /**
         * todo см комментарии в {@link UserRepositoryTest#getAndModifyUserAndPersistMustReflectThisOnDB()}
        */
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
    public void refreshHistory(long userId, Restaurant restaurant)
    {
        LocalDate date = currentDate();
        historyRepository.delete(userId, date);
        if (restaurant != null)
        {
            historyRepository.save(new VoteHistory(date, restaurant, userId));
        }
    }

    @Override
    public User getByEmail(String email)
    {
        return repository.getByEmail(email);
    }
}
