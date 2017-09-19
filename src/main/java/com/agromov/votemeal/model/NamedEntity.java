package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by A.Gromov on 24.05.2017.
 */
@EqualsAndHashCode(of = "name", callSuper = true)
@Getter
@Setter
@MappedSuperclass
public class NamedEntity extends BaseEntity {

  @JsonView({ViewWhen.GetVoteHistory.class, ViewWhen.SendUser.class})
  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  NamedEntity(long id, String name) {
    super(id);
    this.name = name;
  }

  NamedEntity() {}

}
