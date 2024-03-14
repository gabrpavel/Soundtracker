package com.soundtracker.backend.controller.movie;

import com.soundtracker.backend.dto.response.movie.MovieDto;
import com.soundtracker.backend.model.Actor;
import com.soundtracker.backend.model.Director;
import com.soundtracker.backend.model.Genre;
import com.soundtracker.backend.model.Movie;
import com.soundtracker.backend.repository.ActorRepository;
import com.soundtracker.backend.repository.DirectorRepository;
import com.soundtracker.backend.repository.GenreRepository;
import com.soundtracker.backend.repository.MovieRepository;
import com.soundtracker.backend.service.movie.KinopoiskService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api-soudtracker")
public class MovieController {

    private final KinopoiskService kinopoiskService;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;

    public MovieController(KinopoiskService kinopoiskService, MovieRepository movieRepository, GenreRepository genreRepository, ActorRepository actorRepository, DirectorRepository directorRepository) {
        this.kinopoiskService = kinopoiskService;
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
    }

    @GetMapping("/movie/details")
    public MovieDto getMovieInfo(@RequestParam("id") Long id) throws IOException {
        return kinopoiskService.getMovieDetails(id);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {

        List<Movie> movies = movieRepository.findAll();

        movies.forEach(movie -> {

            List<Genre> genres = genreRepository.findGenresByMoviesId(movie.getId());
            List<Actor> actors = actorRepository.findActorsByMoviesId(movie.getId());
            List<Director> directors = directorRepository.findDirectorByMoviesId(movie.getId());

            movie.setGenres(new HashSet<>(genres));
            movie.setActors(new HashSet<>(actors));
            movie.setDirectors(new HashSet<>(directors));
        });

        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }


    @GetMapping("movie/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Not found movie with id = " + id));

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}