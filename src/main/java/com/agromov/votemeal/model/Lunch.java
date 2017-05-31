package com.agromov.votemeal.model;

import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Entity
@Table(name = "lunches", uniqueConstraints = {@UniqueConstraint(name = "lunches_unique_restaurant_name_idx", columnNames = {"restaurant_id", "name"})})
public class Lunch extends NamedEntity
{
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "description")
    private String description = "";

    @Column(name = "enabled", columnDefinition = "defaults true")
    private boolean enabled;

    @Range(min = 10, max = 5000)
    @Column(name = "price")
    private int price;

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

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getPrice()
    {
        return price;
    }
}
