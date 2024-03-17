package com.soundtracker.backend.controller.movie;

import com.soundtracker.backend.service.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию о кино")
    @GetMapping("/info")
    public ResponseEntity<String> getMovieInfo(@RequestParam("id") Long id) {
        ResponseEntity<String> responseFromDatabase = movieService
                .getMovieInfoFromDatabase("/info?id=" + id);

        if (responseFromDatabase.getStatusCode().is2xxSuccessful() && responseFromDatabase.getBody() != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseFromDatabase.getBody());
        }

        // Если информация не найдена в базе данных, делаем запрос к внешнему API
        ResponseEntity<String> responseFromApi = movieService.getMovieInfoFromApi(id);
        if (responseFromApi.getStatusCode().is2xxSuccessful() && responseFromApi.getBody() != null) {
            ResponseEntity<String> response = movieService.processApiResponse(responseFromApi.getBody());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } else {
            return ResponseEntity
                    .status(responseFromApi.getStatusCode())
                    .body("Error getting movie info from database and API");
        }
    }
}