package com.soundtracker.backend.repository.music;

import com.soundtracker.backend.model.music.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
    Optional<Album> findByName(String name);
}
