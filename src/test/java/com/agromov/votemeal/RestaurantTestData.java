package com.agromov.votemeal;

import com.agromov.votemeal.matchers.ModelMatcher;
import com.agromov.votemeal.model.Restaurant;
import com.agromov.votemeal.util.RestaurantBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by A.Gromov on 30.05.2017.
 */
public class RestaurantTestData
{
    public static final ModelMatcher<Restaurant> MATCHER = ModelMatcher.of(Restaurant.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.isClosed(), actual.isClosed())
                            && Objects.equals(expected.getAddress().getStreet(), actual.getAddress().getStreet())
                            && Objects.equals(expected.getAddress().getBuilding(), actual.getAddress().getBuilding())
                            && Objects.equals(expected.getAddress().getRouteGuide(), actual.getAddress().getRouteGuide())
                            && Objects.equals(expected.getWorkingTime().getWorkFrom(), actual.getWorkingTime().getWorkFrom())
                            && Objects.equals(expected.getWorkingTime().getWorkUntil(), actual.getWorkingTime().getWorkUntil())
                    ));

    public static final Restaurant MCDONALDS = new RestaurantBuilder()
            .withId(4)
            .withName("Макдоналдс")
            .withAddress().street("МКАД, 47-й километр")
            .withAddress().building(20)
            .withAddress().routeGuide("ТЦ Наш")
            .withWorkTime().from(9, 0)
            .withWorkTime().until(22, 30)
            .build();

    public static final Restaurant POTATO = new RestaurantBuilder()
            .withId(5)
            .withName("Крошка Картошка")
            .withAddress().street("МКАД, 47-й километр")
            .withAddress().building(20)
            .withAddress().routeGuide("ТЦ Наш")
            .withWorkTime().from(9, 0)
            .withWorkTime().until(22, 0)
            .build();

    public static final Restaurant CHOCO = new RestaurantBuilder()
            .withId(6)
            .withName("Шоколадница")
            .withAddress().street("Солнцевский проспект")
            .withAddress().building(21)
            .withAddress().routeGuide("ТЦ Столица")
            .withWorkTime().from(9, 0)
            .withWorkTime().until(22, 0)
            .build();

    public static final Restaurant BENJAMIN = new RestaurantBuilder()
            .withId(7)
            .withName("Бенджамин")
            .withAddress().street("50 лет Октября")
            .withAddress().building(2)
            .withWorkTime().from(9, 0)
            .withWorkTime().until(0, 0)
            .build();

    public static final Restaurant SUBWAY = new RestaurantBuilder()
            .withId(8)
            .withName("Subway")
            .withAddress().street("50 лет Октября")
            .withAddress().building(10)
            .withWorkTime().from(9, 0)
            .withWorkTime().until(22, 0)
            .build();

    public static final List<Restaurant> RESTAURANTS  = Arrays.asList(SUBWAY, BENJAMIN, POTATO, MCDONALDS, CHOCO);

    public static final int MCDONALDS_ID = MCDONALDS.getId();

    public static Restaurant getCreated()
    {
        return new RestaurantBuilder()
                .withName("New Cafe")
                .withAddress().building(1)
                .withAddress().street("Street")
                .withWorkTime().from(9, 0)
                .withWorkTime().until(22, 0)
                .build();
    }


}
