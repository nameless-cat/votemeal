package com.agromov.votemeal.model;

import javax.persistence.*;
import java.time.LocalTime;
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

    @Embedded
    private Address address;

    @Embedded
    private WorkingTime workingTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    private Set<Lunch> lunches;

    public Restaurant(int id, String name, int vote, Address address, WorkingTime workingTime, Set<Lunch> lunches)
    {
        super(id, name);
        this.vote = vote;
        this.address = address;
        this.workingTime = workingTime;
        this.lunches = lunches;
    }

    @Embeddable
    private static class WorkingTime
    {
        @Column(name = "work_from", nullable = false)
        private LocalTime workFrom;

        @Column(name = "work_until", nullable = false)
        private LocalTime workUntil;
    }

    @Embeddable
    private static class Address
    {
        @Column(name = "street", nullable = false)
        private String street;

        @Column(name = "building", nullable = false)
        private int building;

        @Column(name = "route_guide")
        private String routeGuide;
    }

    public int getVote()
    {
        return vote;
    }

    public void setVote(int vote)
    {
        this.vote = vote;
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
}
