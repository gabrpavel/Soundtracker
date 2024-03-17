package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findActorsByMoviesId(Long movieId);
}
