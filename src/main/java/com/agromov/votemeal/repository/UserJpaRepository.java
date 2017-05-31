package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Transactional(readOnly = true)
public interface UserJpaRepository
        extends JpaRepository<User, Integer>
{
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id) throws EntityNotFoundException;

    @Override
    @Transactional
    User save(User user);

    @Override
    User findOne(Integer integer);

    List<User> findAllByOrderByName();

    List<User> findAllByOrderByName(Pageable pageable);
}
