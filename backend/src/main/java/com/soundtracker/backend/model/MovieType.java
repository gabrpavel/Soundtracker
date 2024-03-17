package com.soundtracker.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_types")
public class MovieType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Movie> movies;

    public MovieType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
