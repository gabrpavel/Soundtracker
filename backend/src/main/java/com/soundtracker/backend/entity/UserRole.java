package com.soundtracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String role;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
