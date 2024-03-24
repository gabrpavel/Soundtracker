package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.MovieScreenshot;
import com.soundtracker.backend.model.movie.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @NotNull
    Optional<Movie> findById(@NotNull Long id);
    boolean existsMovieById(Long id);
}
