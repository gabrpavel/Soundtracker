package com.soundtracker.backend.controller.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.service.movie.DBMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с кино из базы данных
 */
@RestController
@Tag(name = "Database Movie Controller",
        description = "Контроллер для работы с информацией о кино из базы данных")
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api-soudtracker/db-movie")
public class DBMovieController {

    private final DBMovieService dbMovieService;

    /**
     * Конструктор для инициализации сервиса работы с кино
     *
     * @param dbMovieService сервис для работы с кино из базы данных
     */
    public DBMovieController(DBMovieService dbMovieService) {
        this.dbMovieService = dbMovieService;
    }

    /**
     * Получение списка кино из базы данных
     *
     * @return список всех фильмов в формате JSON
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    @Operation(summary = "Получение списка кино", description = "Возвращает информацию о всем кино из базы данных")
    @GetMapping("/all-movies")
    public ResponseEntity<String> allMovies() throws JsonProcessingException {
        String response = dbMovieService.getAllMovies();
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Сохранение кино в базе данных
     *
     * @param movie кино для сохранения
     * @return ответ с сохраненным кино в формате JSON
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    @Operation(summary = "Сохранение кино", description = "Сохраняет кино в базе данных")
    @PostMapping("/save")
    public ResponseEntity<String> saveMovie(@RequestBody Movie movie) throws JsonProcessingException {
        String response = dbMovieService.saveMovie(movie);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Получение информации о кино из базы данных по его идентификатору
     *
     * @param id идентификатор кино
     * @return информация о кино в формате JSON
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию о кино")
    @GetMapping("/info")
    public ResponseEntity<String> getMovieById(@RequestParam Long id) throws JsonProcessingException {
        String response = dbMovieService.getMovieById(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
