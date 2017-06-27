package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(name = "restaurants_unique_name_id_idx", columnNames = {"id", "name"})})
public class Restaurant extends NamedEntity
{
    @Column(name = "closed", nullable = false, columnDefinition = "default false")
    private boolean closed;

    @JsonView(ViewWhen.GetVoteHistory.class)
    @NotNull
    private Address address;

    @NotNull
    private WorkingTime workingTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Lunch> lunches = new HashSet<>();

    public Restaurant(long id, String name, Address address, WorkingTime workingTime, Set<Lunch> lunches)
    {
        super(id, name);
        this.address = address;
        this.workingTime = workingTime;
        this.lunches = lunches;
    }

    public Restaurant(Restaurant restaurant)
    {
        this(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getWorkingTime(), restaurant.getLunches());
    }

    public Restaurant(){}

    public void addLunch(Lunch lunch)
    {
        lunch.setRestaurant(this);
        lunches.add(lunch);
    }

    public boolean isClosed()
    {
        return closed;
    }

    public void setClosed(boolean closed)
    {
        this.closed = closed;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public WorkingTime getWorkingTime()
    {
        return workingTime;
    }

    public void setWorkingTime(WorkingTime workingTime)
    {
        this.workingTime = workingTime;
    }

    public Set<Lunch> getLunches()
    {
        return lunches;
    }

    public void setLunches(Set<Lunch> lunches)
    {
        this.lunches = lunches;
    }

    @Override
    public String toString()
    {
        return "Restaurant{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                "address={" + address + '}' +
                ", workingTime={" + workingTime + '}' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Restaurant that = (Restaurant) o;

        if (isClosed() != that.isClosed()) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        return getWorkingTime() != null ? getWorkingTime().equals(that.getWorkingTime()) : that.getWorkingTime() == null;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (isClosed() ? 1 : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getWorkingTime() != null ? getWorkingTime().hashCode() : 0);
        return result;
    }
}
