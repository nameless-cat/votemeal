package com.agromov.votemeal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Entity
@Table(name = "lunches", uniqueConstraints = {@UniqueConstraint(name = "lunches_unique_restaurant_name_idx", columnNames = {"restaurant_id", "name"})})
public class Lunch extends NamedEntity
{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "description", columnDefinition = "defaults ''")
    private String description = "";

    @Column(name = "enabled", columnDefinition = "defaults true")
    private boolean enabled;

    @Range(min = 10, max = 5000)
    @Column(name = "price")
    private float price;

    public Lunch(int id, String name, Restaurant restaurant, String description)
    {
        super(id, name);
        this.restaurant = restaurant;
        this.description = description;
    }

    public Lunch() {}

    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getPrice()
    {
        return price;
    }

    @Override
    public String toString()
    {
        return "Lunch{" +
                "name='" + this.getName() + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", price=" + price +
                '}';
    }
}
