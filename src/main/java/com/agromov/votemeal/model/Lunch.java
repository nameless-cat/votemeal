package com.agromov.votemeal.model;

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
    private Restaurant restaurant;

    @Column(name = "description")
    private String description;

    public Lunch(int id, String name, Restaurant restaurant, String description)
    {
        super(id, name);
        this.restaurant = restaurant;
        this.description = description;
    }
}
