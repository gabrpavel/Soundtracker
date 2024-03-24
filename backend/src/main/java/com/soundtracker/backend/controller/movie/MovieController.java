package com.soundtracker.backend.controller.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.service.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RequestMapping("/api-soudtracker/movie")
@Tag(name = "Movie Controller",
        description = "Контроллер для работы с информацией о кино")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Получение информации о кино по его идентификатору.
     *
     * @param id идентификатор кино
     * @return ответ с информацией о кино в формате JSON
     */
    // MovieController.java
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию о кино")
    @GetMapping("/info")
    public ResponseEntity<Movie> getMovieInfo(@RequestParam("id") Long id) {
        Optional<Movie> movieOptional = movieService.getMovieInfoFromDatabase("/info?id=" + id);
        if (movieOptional.isEmpty()) {
            movieOptional = movieService.getMovieInfoFromApi(id);
        }
        return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Operation(summary = "Обновление информации о кино", description = "Обновляет информацию о кино по его ID")
    @GetMapping("/update")
    public ResponseEntity<String> updateMovieInfo(@RequestParam("id") Long id) {
        Optional<Movie> movieOptional = movieService.getMovieInfoFromApi(id);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Movie movie = movieOptional.get();
        ResponseEntity<String> response = movieService.updatingMovieResponse(movie);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    /**
     * Установка альбома для кино по его идентификатору.
     *
     * @param id идентификатор кино
     * @return ответ с установленным альбомом для кино в формате JSON
     */
    @GetMapping("/set-album")
    public ResponseEntity<String> setAlbum(@RequestParam("id") Long id) {
        try {
            ResponseEntity<String> response = movieService.setAlbum(id);
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}