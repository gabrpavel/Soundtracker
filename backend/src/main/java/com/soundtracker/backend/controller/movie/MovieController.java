package com.soundtracker.backend.controller.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soundtracker.backend.repository.MovieRepository;
import com.soundtracker.backend.service.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api-soudtracker/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getMovieInfo(@RequestParam("id") Long id) {
        ResponseEntity<String> responseFromDatabase = movieService.getMovieInfoFromDatabase("/movie?id=" + id);

        if (responseFromDatabase.getStatusCode().is2xxSuccessful()) {
            if (responseFromDatabase.getBody() != null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(responseFromDatabase.getBody());
            } else {
                // Если тело ответа пустое, отправляем запрос к внешнему API
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
                            .body("Error getting movie info from API");
                }
            }
        } else {
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


}