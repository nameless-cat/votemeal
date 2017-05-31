package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.Gromov on 31.05.2017.
 */
@Transactional(readOnly = true)
public interface LunchJpaRepository
        extends JpaRepository<Lunch, Integer>
{
    Lunch findByIdAndRestaurantId(int restaurantId, int id);

    List<Lunch> findAllByRestaurantIdOrderByName(int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Lunch l WHERE l.id=:id AND l.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Transactional
    Lunch save(Lunch lunch);
}
