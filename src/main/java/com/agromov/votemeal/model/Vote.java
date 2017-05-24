package com.agromov.votemeal.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by A.Gromov on 24.05.2017.
 */
@Entity
@Table(name = "vote_history")
public class Vote extends BaseEntity
{
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
