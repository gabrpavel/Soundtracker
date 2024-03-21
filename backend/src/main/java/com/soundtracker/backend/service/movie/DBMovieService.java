package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.dto.response.movie.MovieDto;
import com.soundtracker.backend.model.movie.Genre;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.repository.movie.MovieRepository;
import com.soundtracker.backend.repository.movie.MovieScreenshotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с кино из базы данных
 */
@Service
public class DBMovieService {

    private final MovieRepository movieRepository;
    private final ObjectMapper objectMapper;

    public DBMovieService(MovieRepository movieRepository, ObjectMapper objectMapper) {

        this.movieRepository = movieRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Получение всего списка кино из базы данных
     *
     * @return строковое представление JSON-массива всех фильмов
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String getAllMovies() throws JsonProcessingException {
        List<Movie> movies = movieRepository.findAll();
        return objectMapper.writeValueAsString(movies);
    }

    /**
     * Сохранение кино в базе данных
     *
     * @param movie кино для сохранения
     * @return строковое представление JSON сохраненного кино
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String saveMovie(Movie movie) throws JsonProcessingException {
        movie.getMovieScreenshots().forEach(movieScreenshot -> movieScreenshot.setMovie(movie));
        movieRepository.save(movie);
        return objectMapper.writeValueAsString(movie);
    }

    /**
     * Получение фильма по его идентификатору из базы данных
     *
     * @param id идентификатор фильма
     * @return строковое представление JSON найденного фильма, если он существует; в противном случае null
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String getMovieById(Long id) throws JsonProcessingException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            return objectMapper.writeValueAsString(movie);
        } else {
            return null;
        }
    }

    /**
     * Удаление кино по его идентификатору из базы данных
     *
     * @param id идентификатор кино
     * @return строковое представление JSON сообщения об удалении кино
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String deleteMovieById(Long id) throws JsonProcessingException {
        movieRepository.deleteById(id);
        return objectMapper.writeValueAsString("Movie with id " + id + " was deleted");
    }

    /**
     * Обновление информации о кино в базе данных
     *
     * @param movie кино для обновления
     * @return строковое представление JSON обновленного кино
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String updateMovie(Movie movie) throws JsonProcessingException {
        movie.getMovieScreenshots().forEach(movieScreenshot -> movieScreenshot.setMovie(movie));
        movieRepository.save(movie);
        return objectMapper.writeValueAsString(movie);
    }

    /**
     * Получение всего списка кино из базы данных в виде DTO
     *
     * @return строковое представление JSON-массива всех фильмов в виде DTO
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String getAllMoviesDTO() throws JsonProcessingException {
        List<Movie> movies = movieRepository.findAll();
        return objectMapper.writeValueAsString(movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

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
