package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.MovieScreenshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieScreenshotRepository extends JpaRepository<MovieScreenshot, String> {
    List<MovieScreenshot> findMovieScreenshotsByMovieId(Long movieId);
}
