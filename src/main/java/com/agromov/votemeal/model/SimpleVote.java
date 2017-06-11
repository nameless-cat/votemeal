package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 10.06.2017.
 */
@Entity
@Table(name = "votes")
public class SimpleVote
{
    @EmbeddedId
    private SimpleVotePK pk;

    @Column(name = "votes")
    private Integer votes;

    public SimpleVote()
    {
        this.pk = new SimpleVotePK();
        this.pk.date = currentDate();
        this.votes = 0;
    }

    public SimpleVote(long restaurantId)
    {
        this();
        this.pk.restaurantId = restaurantId;
    }

    public static class SimpleVotePK implements Serializable
    {
        @Column(name = "date", updatable = false)
        private LocalDate date;

        @Column(name = "restaurant_id")
        private Long restaurantId;

        public SimpleVotePK() {}

        public SimpleVotePK(LocalDate date, Long restaurantId)
        {
            this.date = date;
            this.restaurantId = restaurantId;
        }

        public LocalDate getDate()
        {
            return date;
        }

        public void setDate(LocalDate date)
        {
            this.date = date;
        }

        public Long getRestaurantId()
        {
            return restaurantId;
        }

        public void setRestaurantId(long restaurantId)
        {
            this.restaurantId = restaurantId;
        }
    }

    public SimpleVotePK getPk()
    {
        return pk;
    }

    public void setPk(SimpleVotePK pk)
    {
        this.pk = pk;
    }

    public Integer getVotes()
    {
        return votes;
    }

    public void setVotes(Integer votes)
    {
        this.votes = votes;
    }
}
