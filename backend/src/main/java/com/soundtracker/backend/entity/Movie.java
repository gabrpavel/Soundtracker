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
    @Column(name = "release_year")
    private int releaseYear;
    @Column(name = "description")
    private String description;
    @Column(name = "length")
    private int length;
    @Column(name = "poster")
    private String poster;

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

    @ManyToMany
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors;
}
