package com.soundtracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String email;
    private String password;
    private String login;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;
}
