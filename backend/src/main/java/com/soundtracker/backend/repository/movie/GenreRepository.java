package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
    List<Genre> findGenresByMoviesId(Long movieId);
}
