package com.soundtracker.backend.service.movie;

import com.soundtracker.backend.api.KinopoiskAPI;
import com.soundtracker.backend.dto.response.movie.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KinopoiskService {

    private final KinopoiskAPI kinopoiskAPI;

    public MovieDto getMovieDetails(Long id) throws IOException {
        return kinopoiskAPI.searchMovie(id);
    }
}
