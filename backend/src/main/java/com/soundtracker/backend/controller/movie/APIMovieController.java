package com.soundtracker.backend.controller.movie;

import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.service.movie.APIMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Контроллер для работы с кино из внешнего API
 */
@RestController
@Tag(name = "API Movie Controller",
        description = "Контроллер для работы с информацией о кино из внешнего API (Kinopoisk API)")
@CrossOrigin(origins = "http://localhost:4200")
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
     * @throws IOException если возникают проблемы при обращении к внешнему API
     */
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию о кино")
    @GetMapping("/info")
    public ResponseEntity<String> getMovieInfo(@RequestParam("id") Long id) throws IOException {

        String response = apiMovieService.getMovieDetails(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Получение информации о кино по его названию
     *
     * @param title название кино
     * @return объект кино в случае успеха
     * @throws IOException если возникают проблемы при обращении к внешнему API
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает информации о кино")
    @GetMapping("/info-by-title")
    public Movie getMovie(@RequestParam("title") String title) throws IOException {
        return apiMovieService.getMovieByTitle(title);
    }
}
