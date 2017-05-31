package com.agromov.votemeal.util;

import com.agromov.votemeal.model.Lunch;
import com.agromov.votemeal.model.Restaurant;

/**
 * Created by A.Gromov on 31.05.2017.
 */
public class LunchBuilder
{
    private Lunch lunch;

    public LunchBuilder()
    {
        lunch = new Lunch();
        lunch.setEnabled(true);
    }

    public LunchBuilder(Lunch lunch)
    {
        this.lunch = new Lunch();
        this.lunch.setId(lunch.getId());
        this.lunch.setName(lunch.getName());
        this.lunch.setRestaurant(lunch.getRestaurant());
        this.lunch.setPrice(lunch.getPrice());
        this.lunch.setEnabled(lunch.isEnabled());
        this.lunch.setDescription(lunch.getDescription());

    }


    public LunchBuilder withId(int id)
    {
        lunch.setId(id);
        return this;
    }

    public LunchBuilder withName(String name)
    {
        lunch.setName(name);
        return this;
    }

    public LunchBuilder withPrice(int price)
    {
        lunch.setPrice(price);
        return this;
    }

    public LunchBuilder withDescription(String description)
    {
        lunch.setDescription(description);
        return this;
    }

    public LunchBuilder withRestaurant(Restaurant restaurant)
    {
        lunch.setRestaurant(restaurant);
        return this;
    }

    public Lunch build()
    {
        return lunch;
    }
}
