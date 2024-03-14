package com.soundtracker.backend.repository;

import com.soundtracker.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie save(Movie movie);
    Optional<Movie> findById(Long id);

    List<Movie> findMoviesByGenresId(Long genreId);
}
