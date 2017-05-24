package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by A.Gromov on 24.05.2017.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity
{
    @Column(name = "name", nullable = false)
    private String name;

    NamedEntity(int id, String name)
    {
        super(id);
        this.name = name;
    }

    NamedEntity()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
