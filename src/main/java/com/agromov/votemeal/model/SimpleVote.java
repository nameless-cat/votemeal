package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static com.agromov.votemeal.util.DateTimeUtil.currentDate;

/**
 * Created by A.Gromov on 10.06.2017.
 */
@Entity
@Table(name = "votes")
public class SimpleVote
    extends BaseVote
{
    @EmbeddedId
    private SimpleVotePK pk;

    public SimpleVote()
    {
        this.pk = new SimpleVotePK();
        this.pk.date = currentDate();
        setVotes(0);;
    }

    public SimpleVote(long restaurantId)
    {
        this();
        this.pk.restaurantId = restaurantId;
    }

    public static class SimpleVotePK implements Serializable
    {
        @NotNull
        @Column(name = "date", updatable = false)
        private LocalDate date;

        @NotNull
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

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimpleVotePK that = (SimpleVotePK) o;

            if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;
            return getRestaurantId() != null ? getRestaurantId().equals(that.getRestaurantId()) : that.getRestaurantId() == null;
        }

        @Override
        public int hashCode()
        {
            int result = getDate() != null ? getDate().hashCode() : 0;
            result = 31 * result + (getRestaurantId() != null ? getRestaurantId().hashCode() : 0);
            return result;
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
}
