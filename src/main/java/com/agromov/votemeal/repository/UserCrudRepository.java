package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Transactional(readOnly = true)
public interface UserCrudRepository extends JpaRepository<User, Integer>
{
}
