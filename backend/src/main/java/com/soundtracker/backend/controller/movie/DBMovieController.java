package com.soundtracker.backend.controller.movie;

import com.soundtracker.backend.dto.response.movie.MovieDto;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.service.movie.DBMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для работы с кино из базы данных
 */
@RestController
@Tag(name = "Database Movie Controller",
        description = "Контроллер для работы с информацией о кино из базы данных")
@CrossOrigin(origins = {"http://frontend:4200", "http://localhost:3000"})
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
     * @return список всех фильмов
     */
    @Operation(summary = "Получение списка кино", description = "Возвращает всю информацию о кино из базы данных")
    @GetMapping("/all-movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            List<Movie> movieList = new ArrayList<>(dbMovieService.getAllMovies());
            if (movieList.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Получение списка кино из базы данных в виде DTO
     *
     * @return список всех фильмов в виде DTO
     */
    @Operation(summary = "Получение списка кино в формате DTO", description = "Возвращает информацию о всем кино из базы данных в виде DTO")
    @GetMapping("/all-movies-dto")
    public ResponseEntity<List<MovieDto>> getAllMoviesDto() {
        try {
            List<MovieDto> movieDtoList = new ArrayList<>(dbMovieService.getAllMoviesDTO());
            if (movieDtoList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return new ResponseEntity<>(movieDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Сохранение кино в базе данных
     *
     * @param movie кино для сохранения
     * @return ответ с информацией о результате сохранения
     */
    @Operation(summary = "Сохранение кино", description = "Сохраняет кино в базе данных")
    @PostMapping("/save")
    public ResponseEntity<String> saveMovie(@RequestBody Movie movie) {
        try {
            dbMovieService.saveMovie(movie);
            return ResponseEntity.status(HttpStatus.OK).body("Movie saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving movie.");
        }
    }

    /**
     * Удаление кино из базы данных
     *
     * @param id идентификатор кино
     * @return ответ с информацией о результате удаления
     */
    @Operation(summary = "Удаление кино", description = "Удаляет кино из базы данных")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMovie(@RequestParam Long id) {
        try {
            String deletingResponse = dbMovieService.deleteMovieById(id);
            if (deletingResponse == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie with id: " + id + " doesn't exist.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(deletingResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting movie.");
        }
    }

    /**
     * Обновление информации о кино в базе данных
     *
     * @param movie кино для обновления
     * @return ответ с информацией о результате обновления
     */
    @Operation(summary = "Обновление кино", description = "Обновляет информацию о кино в базе данных")
    @PutMapping("/update")
    public ResponseEntity<String> updateMovie(@RequestBody Movie movie) {
        try {
            dbMovieService.saveMovie(movie);
            return ResponseEntity.status(HttpStatus.OK).body("Movie updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating movie.");
        }
    }

    /**
     * Получение информации о кино из базы данных по его идентификатору
     *
     * @param id идентификатор кино
     * @return информация о кино
     */
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию о кино")
    @GetMapping("/info")
    public ResponseEntity<Movie> getMovieById(@RequestParam Long id) {
        try {
            Movie response = dbMovieService.getMovieById(id);
            if (response == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
