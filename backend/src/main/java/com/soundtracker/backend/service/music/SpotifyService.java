package com.soundtracker.backend.service.music;

import com.soundtracker.backend.api.SpotifyAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyAPI spotifyAPI;

    public String getArtistData(String artistID) throws IOException {
        return spotifyAPI.getArtistData(artistID);
    }
}
