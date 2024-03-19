package com.soundtracker.backend.repository.movie;

import com.soundtracker.backend.model.movie.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findImagesByMovieId(Long movieId);
}
