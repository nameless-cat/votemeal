package com.agromov.votemeal.model;

import com.agromov.votemeal.web.ViewWhen;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by A.Gromov on 22.05.2017.
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends NamedEntity
{
    @JsonView(ViewWhen.SendUser.class)
    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @JsonView(ViewWhen.SendUser.class)
    @Column(name = "registered", columnDefinition = "timestamp default now()")
    private LocalDateTime registered;

    @JsonView(ViewWhen.SendUser.class)
    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled;

    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @BatchSize(size = 200)
    private Set<Role> roles = new HashSet<>();

    public User()
    {}

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.isEnabled(), u.getRegistered(), u.getRoles());
    }

    public User(Long id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, true, LocalDateTime.now(), EnumSet.of(role, roles));
    }

    public User(long id, String name, String email, String password, boolean enabled, LocalDateTime  registered, Collection<Role> roles)
    {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Collection<Role> roles)
    {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public LocalDateTime getRegistered()
    {
        return registered;
    }

    public void setRegistered(LocalDateTime registered)
    {
        this.registered = registered;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
