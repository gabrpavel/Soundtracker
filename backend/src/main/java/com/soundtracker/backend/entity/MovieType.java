package com.soundtracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "movie_types")
public class MovieType {

    @Id
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "type")
    private List<Movie> movies;
}
