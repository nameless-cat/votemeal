package com.agromov.votemeal.util;

import com.agromov.votemeal.model.Address;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.model.WorkingTime;

import java.time.LocalTime;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public class RestaurantBuilder
{
    private Restaurant restaurant;
    private AddressBuilder addressBuilder;
    private WorkTimeBuilder workTimeBuilder;


    public RestaurantBuilder()
    {
        restaurant = new Restaurant();
    }


    public RestaurantBuilder withName(String name)
    {
        restaurant.setName(name);
        return this;
    }

    public AddressBuilder withAddress()
    {
        if (addressBuilder == null)
            addressBuilder = new AddressBuilder();
        return addressBuilder;
    }

    public WorkTimeBuilder withWorkTime()
    {
        if (workTimeBuilder == null)
            workTimeBuilder = new WorkTimeBuilder();
        return workTimeBuilder;
    }

    public Restaurant build()
    {
        return restaurant;
    }

    public RestaurantBuilder withId(int id)
    {
        restaurant.setId(id);
        return this;
    }

    public RestaurantBuilder withClosedDoors(boolean closed)
    {
        restaurant.setClosed(closed);
        return this;
    }

    public class AddressBuilder
    {
        private AddressBuilder() {
            restaurant.setAddress(new Address());
        }

        public RestaurantBuilder street(String street)
        {
            restaurant.getAddress().setStreet(street);
            return RestaurantBuilder.this;
        }

        public RestaurantBuilder building(int building)
        {
            restaurant.getAddress().setBuilding(building);
            return RestaurantBuilder.this;
        }

        public RestaurantBuilder routeGuide(String routeGuide)
        {
            restaurant.getAddress().setRouteGuide(routeGuide);
            return RestaurantBuilder.this;
        }
    }

    public class WorkTimeBuilder
    {
        private WorkTimeBuilder() {
            restaurant.setWorkingTime(new WorkingTime());
        }

        public RestaurantBuilder from(int hour, int minutes)
        {
            restaurant.getWorkingTime().setWorkFrom(LocalTime.of(hour, minutes));
            return RestaurantBuilder.this;
        }

        public RestaurantBuilder until(int hour, int minutes)
        {
            restaurant.getWorkingTime().setWorkUntil(LocalTime.of(hour, minutes));
            return RestaurantBuilder.this;
        }
    }
}
