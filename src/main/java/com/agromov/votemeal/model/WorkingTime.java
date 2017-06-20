package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@Embeddable
public class WorkingTime
{
    @NotNull
    @Column(name = "work_from", nullable = false)
    private LocalTime workFrom;

    @NotNull
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkingTime that = (WorkingTime) o;

        if (getWorkFrom() != null ? !getWorkFrom().equals(that.getWorkFrom()) : that.getWorkFrom() != null)
            return false;
        return getWorkUntil() != null ? getWorkUntil().equals(that.getWorkUntil()) : that.getWorkUntil() == null;
    }

    @Override
    public int hashCode()
    {
        int result = getWorkFrom() != null ? getWorkFrom().hashCode() : 0;
        result = 31 * result + (getWorkUntil() != null ? getWorkUntil().hashCode() : 0);
        return result;
    }
}
