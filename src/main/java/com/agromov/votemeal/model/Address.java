package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@EqualsAndHashCode(of = {"street", "building", "routeGuide"})
@Getter
@Setter
@Embeddable
public class Address {

  @JsonView(ViewWhen.GetVoteHistory.class)
  @NotBlank
  @Column(name = "street", nullable = false)
  private String street;

  @JsonView(ViewWhen.GetVoteHistory.class)
  @NotNull
  @Min(1)
  @Column(name = "building", nullable = false)
  private Integer building;

  @Column(name = "route_guide", columnDefinition = "varchar default ''")
  private String routeGuide = "";

  @Override
  public String toString() {
    return "Address{" +
        "street='" + street + '\'' +
        ", building=" + building +
        ", routeGuide='" + routeGuide + '\'' +
        '}';
  }
}
