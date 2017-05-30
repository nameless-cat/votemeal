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
    @Column(name = "vote", nullable = false)
    private int vote;

    @Column(name = "closed", nullable = false, columnDefinition = "default false")
    private boolean closed;

    @Embedded
    private Address address;

    @Embedded
    private WorkingTime workingTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Lunch> lunches = new HashSet<>();

    public Restaurant(int id, String name, int vote, Address address, WorkingTime workingTime, Set<Lunch> lunches)
    {
        super(id, name);
        this.vote = vote;
        this.address = address;
        this.workingTime = workingTime;
        this.lunches = lunches;
    }

    public Restaurant(){}

    public int getVote()
    {
        return vote;
    }

    public void setVote(int vote)
    {
        this.vote = vote;
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
}
