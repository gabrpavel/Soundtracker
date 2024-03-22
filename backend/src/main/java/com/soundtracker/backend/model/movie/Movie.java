package com.soundtracker.backend.model.movie;

import com.soundtracker.backend.model.music.Album;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Кино")
public class Movie {

    @Id
    @Schema(description = "ID кино", example = "4996")
    private Long id;

    @Schema(description = "Название на русском языке", example = "Общество мертвых поэтов")
    @Column(name = "ru_title")
    private String ruTitle;

    @Schema(description = "Альтернативное название", example = "Dead Poets Society")
    @Column(name = "en_title")
    private String enTitle;

    @Schema(description = "Год производства", example = "1989")
    @Column(name = "release_year")
    private int releaseYear;

    @Schema(description = "Описание",
            example = "Джон Китинг — новый преподаватель английской словесности в консервативном американском колледже...")
    @Column(name = "description")
    private String description;

    @Schema(description = "Продолжительность", example = "128")
    @Column(name = "length")
    private int length;

    @Schema(description = "Постер", example = "https://image.openmoviedb.com/kinopoisk-images/1599028/66058481-9b77-4b51-9d4b-65db5cd796d2/orig")
    @Column(name = "poster")
    private String poster;

    @Schema(description = "Тип кино (movie | tv-series | cartoon | anime | animated-series | tv-show)", example = "movie")
    @ManyToOne(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST
    })
    @JoinColumn(name = "type_id")
    private MovieType type;

    @Schema(description = "Жанр", example = "драма")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    @JoinTable(
            name = "movie_genres",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres = new HashSet<>();

    @Schema(description = "Актерский состав")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @Schema(description = "Режиссер(-ы)")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();

    @Schema(description = "Изображения")
    @OneToMany(mappedBy = "movie",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<MovieScreenshot> movieScreenshots = new HashSet<>();

    @Schema(description = "Альбом", example = "Whiplash (Original Motion Picture Soundtrack)")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private Album album;

    public Movie(Long id, String ruTitle, String enTitle, int releaseYear,
                 String description, int length, String poster) {
        this.id = id;
        this.ruTitle = ruTitle;
        this.enTitle = enTitle;
        this.releaseYear = releaseYear;
        this.description = description;
        this.length = length;
        this.poster = poster;
    }

    public Movie(Long id) {
        this.id = id;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getMovies().add(this);
    }

    public void removeGenre(Long genreId) {
        Genre genre = this.genres.stream().filter(g -> g.getId().equals(genreId))
                .findFirst().orElse(null);
        if (genre != null) {
            this.genres.remove(genre);
            genre.getMovies().remove(this);
        }
    }

    public void addActor(Actor actor) {
        this.actors.add(actor);
        actor.getMovies().add(this);
    }

    public void removeActor(Long actorId) {
        Actor actor = this.actors.stream().filter(g -> g.getId().equals(actorId))
                .findFirst().orElse(null);
        if (actor != null) {
            this.actors.remove(actor);
            actor.getMovies().remove(this);
        }
    }

    public void addDirector(Director director) {
        this.directors.add(director);
        director.getMovies().add(this);
    }

    public void removeDirectors(Long directorId) {
        Director director = this.directors.stream().filter(g -> g.getId().equals(directorId))
                .findFirst().orElse(null);
        if (director != null) {
            this.directors.remove(director);
            director.getMovies().remove(this);
        }
    }

    public void addMovieScreenshot(MovieScreenshot movieScreenshot) {
        this.movieScreenshots.add(movieScreenshot);
        movieScreenshot.setMovie(this);
    }

    public void removeMovieScreenshot(String movieScreenshotId) {
        MovieScreenshot movieScreenshot = this.movieScreenshots.stream().filter(g -> g.getId().equals(movieScreenshotId))
                .findFirst().orElse(null);
        if (movieScreenshot != null) {
            this.movieScreenshots.remove(movieScreenshot);
            movieScreenshot.setMovie(null);
        }
    }

    public void setAlbum(Album album) {
        if (this.album != null) {
            this.album.setMovie(null);
        }
        this.album = album;
        if (album != null) {
            album.setMovie(this);
        }
    }
}