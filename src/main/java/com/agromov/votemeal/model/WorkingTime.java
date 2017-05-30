package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalTime;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Embeddable
public class WorkingTime
{
    @Column(name = "work_from", nullable = false)
    private LocalTime workFrom;

    @Column(name = "work_until", nullable = false)
    private LocalTime workUntil;

    @Override
    public String toString()
    {
        return "WorkingTime{" +
                "workFrom=" + workFrom +
                ", workUntil=" + workUntil +
                '}';
    }

    public LocalTime getWorkFrom()
    {
        return workFrom;
    }

    public void setWorkFrom(LocalTime workFrom)
    {
        this.workFrom = workFrom;
    }

    public LocalTime getWorkUntil()
    {
        return workUntil;
    }

    public void setWorkUntil(LocalTime workUntil)
    {
        this.workUntil = workUntil;
    }
}
