package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Embeddable
public class Address
{
    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "building", nullable = false)
    private int building;

    @Column(name = "route_guide")
    private String routeGuide = "";


    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public int getBuilding()
    {
        return building;
    }

    public void setBuilding(int building)
    {
        this.building = building;
    }

    public String getRouteGuide()
    {
        return routeGuide;
    }

    public void setRouteGuide(String routeGuide)
    {
        this.routeGuide = routeGuide;
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "street='" + street + '\'' +
                ", building=" + building +
                ", routeGuide='" + routeGuide + '\'' +
                '}';
    }
}
