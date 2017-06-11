package com.agromov.votemeal.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(name = "restaurants_unique_name_id_idx", columnNames = {"id", "name"})})
public class Restaurant extends NamedEntity
{
    @Column(name = "closed", nullable = false, columnDefinition = "default false")
    private boolean closed;

    private Address address;

    private WorkingTime workingTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Lunch> lunches = new HashSet<>();

    public Restaurant(int id, String name, int vote, Address address, WorkingTime workingTime, Set<Lunch> lunches)
    {
        super(id, name);
        this.address = address;
        this.workingTime = workingTime;
        this.lunches = lunches;
    }

    public Restaurant(){}

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
