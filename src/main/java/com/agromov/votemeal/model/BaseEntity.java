package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@EqualsAndHashCode(of = "id")
@MappedSuperclass
@Access(AccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity implements Persistable<Long> {

  @Id
  @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
//    @Access(value = AccessType.PROPERTY)
  @JsonView(ViewWhen.SendUser.class)
  private Long id;

  BaseEntity() {
  }

  BaseEntity(Long id) {
    this.id = id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public boolean isNew() {
    return id == null;
  }
}
