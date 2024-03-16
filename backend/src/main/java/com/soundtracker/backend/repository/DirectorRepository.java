package com.soundtracker.backend.repository;

import com.soundtracker.backend.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    List<Director> findDirectorByMoviesId(Long movieId);
}
