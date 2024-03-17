package com.soundtracker.backend.controller.music;

import com.soundtracker.backend.service.music.SpotifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api-soudtracker/v1")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/artists")
    public ResponseEntity<String> getArtistData(@RequestParam("artist_id") String artistID) throws IOException {
        String response = spotifyService.getArtistData(artistID);
        return ResponseEntity.ok(response);
    }
}