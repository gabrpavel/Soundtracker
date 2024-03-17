package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    List<Director> findDirectorByMoviesId(Long movieId);
}
