package com.agromov.votemeal.service;

import com.agromov.votemeal.model.User;
import com.agromov.votemeal.model.VoteHistory;

import java.util.List;

/**
 * Created by A.Gromov on 12.06.2017.
 */
public interface UserService
{
    User get(Long id);

    User save(User user);

    User update(User user);

    List<VoteHistory> getHistory(Long id);
}
