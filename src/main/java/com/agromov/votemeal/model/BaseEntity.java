package com.agromov.votemeal.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity implements Persistable<Integer>
{
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Access(value = AccessType.PROPERTY)
    private Integer id;

    BaseEntity(){}

    BaseEntity(Integer id)
    {
        this.id = id;
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
