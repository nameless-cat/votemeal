package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by A.Gromov on 24.05.2017.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity
{
    @JsonView({ViewWhen.GetVoteHistory.class, ViewWhen.SendUser.class})
    @Column(name = "name", nullable = false)
    private String name;

    NamedEntity(long id, String name)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NamedEntity that = (NamedEntity) o;

        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
