package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by A.Gromov on 24.05.2017.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "key")
@Entity
@Table(name = "vote_history")
public class VoteHistory {

  @JsonView(ViewWhen.GetVoteHistory.class)
  @EmbeddedId
  private Key key;

  @Getter
  @Setter
  @ToString
  @EqualsAndHashCode(of = {"date", "userId", "restaurant"})
  public static class Key implements Serializable {
    @JsonView(ViewWhen.GetVoteHistory.class)
    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @JsonView(ViewWhen.GetVoteHistory.class)
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    key.restaurant = restaurant;
  }

  public Restaurant getRestaurant() {
    return key.restaurant;
  }

  public void setDate(LocalDate date) {
    key.date = date;
  }

  public LocalDate getDate() {
    return key.date;
  }

  public void setUserId(Long userId) {
    key.userId = userId;
  }

  public Long getUserId() {
    return key.userId;
  }


  public VoteHistory() {
  }

  public VoteHistory(LocalDate date, Restaurant restaurant, Long userId) {
    key = new Key();
    key.date = date;
    key.restaurant = restaurant;
    key.userId = userId;
  }
}
