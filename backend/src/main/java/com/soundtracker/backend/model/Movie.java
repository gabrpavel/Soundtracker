package com.soundtracker.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "genre_id") } )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();


    public Movie(Long id, String ruTitle, String enTitle, int releaseYear, String description, int length, String poster) {
        this.id = id;
        this.ruTitle = ruTitle;
        this.enTitle = enTitle;
        this.releaseYear = releaseYear;
        this.description = description;
        this.length = length;
        this.poster = poster;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getMovies().add(this);
    }

    public void removeGenre(Long genreId) {
        Genre genre = this.genres.stream().filter(g -> g.getId().equals(genreId)).findFirst().orElse(null);
        if(genre != null) {
            this.genres.remove(genre);
            genre.getMovies().remove(this);
        }
    }

    public void addActor(Actor actor) {
        this.actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(Long actorId) {
        Actor actor = this.actors.stream().filter(g -> g.getId().equals(actorId)).findFirst().orElse(null);
        if(actor != null) {
            this.actors.remove(actor);
            actor.getMovies().remove(this);
        }
    }

    public void addDirector(Director director) {
        this.directors.add(director);
        director.getMovies().add(this);
    }

    public void removeDirectors(Long directorId) {
        Director director = this.directors.stream().filter(g -> g.getId().equals(directorId)).findFirst().orElse(null);
        if(director != null) {
            this.directors.remove(director);
            director.getMovies().remove(this);
        }
    }
}