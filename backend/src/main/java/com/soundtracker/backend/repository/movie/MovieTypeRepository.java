package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    Optional<MovieType> findByName(String name);
}
