package com.agromov.votemeal.repository;

import com.agromov.votemeal.model.Restaurant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Transactional(readOnly = true)
public interface RestaurantJpaRepository
    extends JpaRepository<Restaurant, Long> {

  @Override
  Restaurant findOne(Long integer);

  List<Restaurant> findAllByOrderByName();

  @EntityGraph(attributePaths = "lunches")
  @Query("SELECT r FROM Restaurant r WHERE r.forVote=true ORDER BY r.name")
  List<Restaurant> findAllWithActiveVote();

  List<Restaurant> findAllByOrderByName(Pageable pageable);

  @Transactional
  @Modifying
  @Query("UPDATE Restaurant r SET r.closed=true WHERE r.id=:id")
  int close(@Param("id") long id);

  @Transactional
  @Modifying
  @Query("UPDATE Restaurant r SET r.closed=false WHERE r.id=:id")
  int open(@Param("id") long id);

  @Override
  @Transactional
  Restaurant save(Restaurant restaurant);

  @EntityGraph(attributePaths = "lunches")
  @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
  Restaurant findOneWithLunches(@Param("id") long id);

  @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
  Restaurant findById(@Param("id") long id);
}