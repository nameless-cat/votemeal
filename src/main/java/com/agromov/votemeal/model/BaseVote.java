package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

/**
 * Created by A.Gromov on 20.06.2017.
 */
@MappedSuperclass
public class BaseVote
{
    @Min(0)
    @Column(name = "votes", nullable = false)
    private Integer votes;

    public int getVotes()
    {
        return votes;
    }

    public void setVotes(Integer votes)
    {
        this.votes = votes;
    }
}
