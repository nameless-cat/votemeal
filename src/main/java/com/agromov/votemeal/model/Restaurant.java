package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@EqualsAndHashCode(of = {"closed", "address", "workingTime"}, callSuper = true)
@Setter
@Getter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {
    @UniqueConstraint(name = "restaurants_unique_name_id_idx", columnNames = {"id", "name"})})
public class Restaurant extends NamedEntity {

  @Column(name = "closed", nullable = false, columnDefinition = "default false")
  private boolean closed;

  @Column(name = "for_vote", columnDefinition = "default false")
  private boolean forVote;

  @JsonView(ViewWhen.GetVoteHistory.class)
  @NotNull
  private Address address;

  @NotNull
  private WorkingTime workingTime;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
  private Set<Lunch> lunches = new HashSet<>();

  public Restaurant(long id, String name, Address address, WorkingTime workingTime,
      Set<Lunch> lunches) {
    super(id, name);
    this.address = address;
    this.workingTime = workingTime;
    this.lunches = lunches;
  }

  public Restaurant(Restaurant restaurant) {
    this(restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
        restaurant.getWorkingTime(), restaurant.getLunches());
  }

  public Restaurant() {
  }

  @Override
  public String toString() {
    return "Restaurant{" +
        "id='" + getId() + '\'' +
        "name='" + getName() + '\'' +
        "address={" + address + '}' +
        ", workingTime={" + workingTime + '}' +
        '}';
  }
}
