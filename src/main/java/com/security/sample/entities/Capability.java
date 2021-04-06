package com.security.sample.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Capability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "capabilities")
    private Collection<User> users;

    @ManyToMany(mappedBy = "capabilities")
    private Collection<Role> roles;

}
