package com.agromov.votemeal.model;


import com.agromov.votemeal.util.JpaUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static com.agromov.votemeal.util.MessageUtils.REQUIRE_NOT_NULL;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Entity
@Table(name = "votes")
public class Vote
    extends BaseEntity
        implements JpaUtils.BatchUpdatable<Restaurant, Vote>
{
    @Min(0)
    @Column(name = "votes", nullable = false)
    private Integer votes;

    @NotNull
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    public Vote()
    {
        this.setDate(LocalDate.now());
        votes = 0;
    }

    public Vote(LocalDate date, Restaurant restaurant, int votes)
    {
        Objects.requireNonNull(date, REQUIRE_NOT_NULL);
        Objects.requireNonNull(restaurant, REQUIRE_NOT_NULL);

        this.date = date;
        this.restaurant = restaurant;
        this.votes = votes;
    }

    public Restaurant getRestaurant()
    {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public LocalDate getDate()
    {
        return this.date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Integer getVotes()
    {
        return votes;
    }

    public void setVotes(Integer votes)
    {
        this.votes = votes;
    }

    @Override
    public Vote setArgument(Restaurant restaurant)
    {
        this.restaurant = restaurant;
        return this;
    }

    @Override
    public String toString()
    {
        return "Vote{" +
                "date='" + date + "'" +
                "restaurant='" + restaurant.getName() + "'" +
                ", votes='" + votes + "'" +
                '}';
    }
}
