package com.agromov.votemeal.service;

import com.agromov.votemeal.model.Vote;
import com.agromov.votemeal.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by A.Gromov on 14.06.2017.
 */
@Service
public class VoteServiceImpl
        implements VoteService
{
    @Autowired
    private VoteRepository repository;

    @Override
    public List<Vote> get(LocalDate date)
    {
        // todo проверить дату на корректность
        //if (date.isAfter(LocalDate.now()))
        return repository.getAll(date);
    }

    @Transactional
    @Override
    public void add(List<Long> restaurantIds)
    {
        // todo может можно сделать это одной операцией за одно обращение к базе?
        restaurantIds.forEach(repository::addToVote);
    }

    @Override
    public void delete(Long restaurantId) throws EntityNotFoundException
    {
        if (repository.removeFromVote(restaurantId) == 0)
        {
            throw new EntityNotFoundException();
        }
    }
}
