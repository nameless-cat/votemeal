package com.agromov.votemeal.repository;


import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;

import java.util.List;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public interface UserRepository
{
    int delete(long id);

    List<User> getRange(int offset, int limit);

    User save(User object);

    User get(long id);

    List<User> getAll();

    User update(User updated);

    List<VoteHistory> getHistory(Long userId);

    void refreshHistory(long userId, Restaurant restaurant);
}
