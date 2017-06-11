package com.agromov.votemeal.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
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
{
    @EmbeddedId
    private VotePK pk;

    @Column(name = "votes", nullable = false)
    private Integer votes;



    @Embeddable
    public static class VotePK implements Serializable
    {

        @Column(name = "date", updatable = false)
        private LocalDate date;

        @ManyToOne
        @JoinColumn(name = "restaurant_id")
        private Restaurant restaurant;

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

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VotePK votePK = (VotePK) o;

            if (date != null ? !date.equals(votePK.date) : votePK.date != null) return false;
            return restaurant != null ? restaurant.equals(votePK.restaurant) : votePK.restaurant == null;
        }

        @Override
        public int hashCode()
        {
            int result = date != null ? date.hashCode() : 0;
            result = 31 * result + (restaurant != null ? restaurant.hashCode() : 0);
            return result;
        }

    }

    public Vote()
    {
        this.pk = new VotePK();
        this.pk.setDate(LocalDate.now());
        this.votes = 0;
    }


    public Vote(LocalDate date, Restaurant restaurant, int votes)
    {
        Objects.requireNonNull(date, REQUIRE_NOT_NULL);
        Objects.requireNonNull(restaurant, REQUIRE_NOT_NULL);

        this.pk = new VotePK();
        this.pk.setDate(date);
        this.pk.setRestaurant(restaurant);
        this.votes = votes;
    }

    public Restaurant getRestaurant()
    {
        return this.pk.getRestaurant();
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.pk.setRestaurant(restaurant);
    }

    public VotePK getPk()
    {
        return pk;
    }

    public void setPk(VotePK pk)
    {
        this.pk = pk;
    }

    public int getVotes()
    {
        return votes;
    }

    public void setVotes(Integer votes)
    {
        this.votes = votes;
    }

    public LocalDate getDate()
    {
        return this.pk.getDate();
    }

    public void setDate(LocalDate date)
    {
        this.pk.setDate(date);
    }

    @Override
    public String toString()
    {
        return "Vote{" +
                "date='" + pk.getDate() + "'" +
                "restaurant='" + pk.getRestaurant().getName() + "'" +
                ", votes='" + votes + "'" +
                '}';
    }

    /*public Vote()
    {
        this.date = LocalDate.now();
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        Objects.requireNonNull(date);
        this.date = date;
    }

    public void setVotesForToday(List<Restaurant> restaurants)
    {
        Objects.requireNonNull(restaurants);
        votesForToday = new LinkedHashMap<>();
        restaurants.forEach(r -> votesForToday.put(r, 0));
    }

    public Map<Restaurant, Integer> getVotes()
    {
        return votesForToday;
    }

    public void addVote(Restaurant restaurant)
    {
        Objects.requireNonNull(restaurant);
    }

    public void removeVote(Restaurant restaurant)
    {
        Objects.requireNonNull(restaurant);
        Integer votes = votesForToday.get(restaurant.getId());
        Objects.requireNonNull(votes);

        if (votes > 0)
        {
            votesForToday.put(restaurant, votes - 1);
        }
    }*/


}
