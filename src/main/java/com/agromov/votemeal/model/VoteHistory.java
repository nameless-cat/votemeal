package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by A.Gromov on 24.05.2017.
 */
@Entity
@Table(name = "vote_history")
public class VoteHistory
        extends BaseEntity
{
    @JsonView(ViewWhen.GetVoteHistory.class)
    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @JsonView(ViewWhen.GetVoteHistory.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public VoteHistory()
    {
    }

    public VoteHistory(LocalDate date, Restaurant restaurant, Long userId)
    {
        this.date = date;
        this.restaurant = restaurant;
        this.userId = userId;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "VoteHistory{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                '}';
    }
}
