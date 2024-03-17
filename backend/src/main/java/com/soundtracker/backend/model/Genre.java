package com.soundtracker.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    private Set<Movie> movies = new HashSet<>();
}
