package com.soundtracker.backend.controller.movie;

import com.soundtracker.backend.model.Movie;
import com.soundtracker.backend.service.movie.APIMovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api-soudtracker/api-movie")
public class APIMovieController {

    private  final APIMovieService apiMovieService;

    public APIMovieController(APIMovieService apiMovieService) {
        this.apiMovieService = apiMovieService;
    }

    @GetMapping("/details")
    public ResponseEntity<String> getMovieInfo(@RequestParam("id") Long id) throws IOException {

        String response = apiMovieService.getMovieDetails(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
    @GetMapping("/info")
    public Movie getMovie(@RequestParam("title") String title) throws IOException {
        return apiMovieService.getMovieInfo(title);
    }
}
