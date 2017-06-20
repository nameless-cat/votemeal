package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Embeddable
public class Address
{
    @JsonView(ViewWhen.GetVoteHistory.class)
    @NotBlank
    @Column(name = "street", nullable = false)
    private String street;

    @JsonView(ViewWhen.GetVoteHistory.class)
    @NotNull
    @Min(1)
    @Column(name = "building", nullable = false)
    private Integer building;

    @Column(name = "route_guide", columnDefinition = "varchar default ''")
    private String routeGuide = "";


    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public Integer getBuilding()
    {
        return building;
    }

    public void setBuilding(Integer building)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (getBuilding() != address.getBuilding()) return false;
        if (getStreet() != null ? !getStreet().equals(address.getStreet()) : address.getStreet() != null) return false;
        return getRouteGuide() != null ? getRouteGuide().equals(address.getRouteGuide()) : address.getRouteGuide() == null;
    }

    @Override
    public int hashCode()
    {
        int result = getStreet() != null ? getStreet().hashCode() : 0;
        result = 31 * result + getBuilding();
        result = 31 * result + (getRouteGuide() != null ? getRouteGuide().hashCode() : 0);
        return result;
    }
}
