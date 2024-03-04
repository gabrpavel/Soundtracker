package com.soundtracker.backend.entity.user;

import com.soundtracker.backend.entity.UserRole;
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
    @Column(name = "login")
    private String username;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;
}
