package com.soundtracker.backend.service.movie;

import com.soundtracker.backend.dto.response.movie.MovieDto;
import com.soundtracker.backend.model.movie.Genre;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.repository.movie.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с кино из базы данных
 */
@Service
public class DBMovieService {

    private final MovieRepository movieRepository;

    public DBMovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Получение списка кино из базы данных
     *
     * @return список всех фильмов
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Получение всего списка кино из базы данных в виде DTO
     *
     * @return список всех фильмов в виде DTO
     */
    public List<MovieDto> getAllMoviesDTO() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Получение кино из базы данных по его идентификатору
     *
     * @param id идентификатор кино
     * @return кино
     */
    public Movie getMovieById(Long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        return optionalMovie.orElse(null);
    }

    /**
     * Сохранение кино в базу данных
     *
     * @param movie кино для сохранения
     */
    @Transactional
    public void saveMovie(Movie movie) {
        movie.getMovieScreenshots().forEach(movieScreenshot -> movieScreenshot.setMovie(movie));
        movieRepository.save(movie);
    }

    /**
     * Удаление кино из базы данных по его идентификатору
     *
     * @param id идентификатор кино
     * @return строковое представление результата удаления
     */
    @Transactional
    public String deleteMovieById(Long id) {
        if (movieRepository.existsMovieById(id)) {
            movieRepository.deleteById(id);
            return "Movie with id " + id + " was deleted";
        }
        return null;
    }

    /**
     * Преобразование кино в DTO
     *
     * @param movie кино для преобразования
     * @return DTO кино
     */
    private MovieDto convertToDto(Movie movie) {
        return MovieDto.builder().id(movie.getId())
                .ruTitle(movie.getRuTitle())
                .enTitle(movie.getEnTitle())
                .releaseYear(movie.getReleaseYear())
                .length(movie.getLength())
                .type(movie.getType().getName())
                .poster(movie.getPoster())
                .genres(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()))
                .build();
    }
}
