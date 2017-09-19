package com.agromov.votemeal.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by A.Gromov on 30.05.2017.
 */
@EqualsAndHashCode(of = {"workFrom", "workUntil"})
@Setter
@Getter
@Embeddable
public class WorkingTime {

  @NotNull
  @Column(name = "work_from", nullable = false)
  private LocalTime workFrom;

  @NotNull
  @Column(name = "work_until", nullable = false)
  private LocalTime workUntil;

  @Override
  public String toString() {
    return "WorkingTime{" +
        "workFrom=" + workFrom +
        ", workUntil=" + workUntil +
        '}';
  }
}
