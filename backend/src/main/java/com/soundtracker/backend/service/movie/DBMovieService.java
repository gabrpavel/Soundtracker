package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.repository.movie.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * Сохранение фильма в базу данных
     *
     * @param movie фильм для сохранения
     * @return строковое представление JSON сохраненного фильма
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String saveMovie(Movie movie) throws JsonProcessingException {
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
        movieRepository.save(movie);
        return objectMapper.writeValueAsString(movie);
    }
}
