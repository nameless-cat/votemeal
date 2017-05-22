package com.agromov.votemeal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Created by A.Gromov on 22.05.2017.
 */
public class User extends BaseEntity
{
    private String email;

    private String password;

    private LocalDateTime registered;

    private Set<Role> roles;

    private Map<LocalDate, Restaurant> voteHistory;
}
