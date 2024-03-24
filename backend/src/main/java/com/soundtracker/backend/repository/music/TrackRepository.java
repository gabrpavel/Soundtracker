package com.soundtracker.backend.repository.music;

import com.soundtracker.backend.model.music.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
}
