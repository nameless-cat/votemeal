package com.agromov.votemeal.model;

import java.time.LocalTime;
import java.util.Set;

/**
 * Created by A.Gromov on 22.05.2017.
 */
public class Restaurant extends BaseEntity
{
    private int vote;

    private Address address;

    private WorkingTime workingTime;

    private Set<Lunch> lunches;


    private static class WorkingTime
    {
        private LocalTime from;
        private LocalTime to;
    }

    private static class Address
    {
        private String street;

        private int building;

        private String routeGuide;
    }
}
