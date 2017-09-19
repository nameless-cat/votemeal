package com.agromov.votemeal.model;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by A.Gromov on 07.06.2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

  private LocalDate date;

  private Restaurant restaurant;

  private int votes;
}
