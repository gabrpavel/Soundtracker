package com.soundtracker.backend.controller.movie;

import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.service.movie.APIMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с кино из внешнего API
 */
@RestController
@Tag(name = "API Movie Controller",
        description = "Контроллер для работы с информацией о кино из внешнего API (Kinopoisk API)")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RequestMapping("/api-soudtracker/api-movie")
public class APIMovieController {

    private  final APIMovieService apiMovieService;

    /**
     * Конструктор контроллера для внедрения зависимости сервиса APIMovieService
     *
     * @param apiMovieService сервис для работы с данными о кино из внешнего API
     */
    public APIMovieController(APIMovieService apiMovieService) {
        this.apiMovieService = apiMovieService;
    }

    /**
     * Получение информации о кино по его идентификатору
     *
     * @param id идентификатор кино
     * @return ответ от сервера с информацией о кино в формате JSON
     */
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию о кино")
    @GetMapping("/info")
    public ResponseEntity<Movie> getMovieInfo(@RequestParam("id") Long id) {
        try {
            Movie movie = apiMovieService.getMovieDetails(id);
            if (movie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(movie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Получение информации о кино по его названию
     *
     * @param title название кино
     * @return объект кино в случае успеха
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает информацию о кино")
    @GetMapping("/info-by-title")
    public ResponseEntity<Movie> getMovie(@RequestParam("title") String title) {
        try {
            Movie movie = apiMovieService.searchMovieByTitle(title);
            if (movie == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.OK).body(movie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}