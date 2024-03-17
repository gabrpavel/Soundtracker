package com.soundtracker.backend.controller.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soundtracker.backend.model.Movie;
import com.soundtracker.backend.service.movie.DBMovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api-soudtracker/db-movie")
public class DBMovieController {

    private final DBMovieService dbMovieService;

    public DBMovieController(DBMovieService dbMovieService) {
        this.dbMovieService = dbMovieService;
    }

    @GetMapping("/all-movies")
    public ResponseEntity<String> allMovies() throws JsonProcessingException {

        String response = dbMovieService.getAllMovies();
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMovie(@RequestBody Movie movie) throws JsonProcessingException {

        String response = dbMovieService.saveMovie(movie);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/movie")
    public ResponseEntity<String> getMovieById(@RequestParam Long id) throws JsonProcessingException {
        String response = dbMovieService.getMovieById(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
