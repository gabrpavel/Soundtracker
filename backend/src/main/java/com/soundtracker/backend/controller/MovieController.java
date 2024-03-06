package com.soundtracker.backend.controller;

import com.soundtracker.backend.dto.response.movie.MovieDto;
import com.soundtracker.backend.service.movie.KinopoiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api-soudtracker/v1")
public class MovieController {

    private final KinopoiskService kinopoiskService;

    public MovieController(KinopoiskService kinopoiskService) {
        this.kinopoiskService = kinopoiskService;
    }

    @GetMapping("/movie/details")
    public ResponseEntity<MovieDto> getMovieInfo(@RequestParam("id") String id) {
        try {
            MovieDto movieInfo = kinopoiskService.getMovieDetails(id);
            return ResponseEntity.ok(movieInfo);
        } catch (IOException e) {
            //return new ResponseEntity<>("Error getting movie info", HttpStatus.BAD_GATEWAY);
            return null;
        }
    }
}