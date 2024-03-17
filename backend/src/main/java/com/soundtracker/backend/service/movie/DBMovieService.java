package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.Movie;
import com.soundtracker.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DBMovieService {

    private final MovieRepository movieRepository;
    private final ObjectMapper objectMapper;

    public DBMovieService(MovieRepository movieRepository, ObjectMapper objectMapper) {
        this.movieRepository = movieRepository;
        this.objectMapper = objectMapper;
    }

    public String getAllMovies() throws JsonProcessingException {
        List<Movie> movies = movieRepository.findAll();
        return objectMapper.writeValueAsString(movies);
    }

    public String saveMovie(Movie movie) throws JsonProcessingException {
        movieRepository.save(movie);
        return objectMapper.writeValueAsString(movie);
    }

    public String getMovieById(Long id) throws JsonProcessingException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            return objectMapper.writeValueAsString(movie);
        } else {
            return null;
        }
    }
}
