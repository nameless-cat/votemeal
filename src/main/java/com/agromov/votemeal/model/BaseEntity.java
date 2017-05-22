package com.agromov.votemeal.model;

import org.springframework.data.domain.Persistable;

/**
 * Created by A.Gromov on 22.05.2017.
 */
public class BaseEntity implements Persistable<Integer>
{
    private Integer id;

    private String name;

    public BaseEntity()
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

    public void setId(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public boolean isNew()
    {
        return id == null;
    }
}
