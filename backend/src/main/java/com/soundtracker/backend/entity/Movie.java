package com.soundtracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    private Long id;

    @Column(name = "ru_title")
    private String ruTitle;
    @Column(name = "en_title")
    private String enTitle;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private MovieType type;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}
