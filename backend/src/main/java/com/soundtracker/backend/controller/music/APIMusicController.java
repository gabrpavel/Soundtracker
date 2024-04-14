package com.soundtracker.backend.controller.music;

import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.service.music.APIMusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с музыкой из внешнего API
 */
@RestController

@RequestMapping("/api-soudtracker/api-music")
@CrossOrigin(origins = {"http://frontend:4200", "http://localhost:3000"})
@Tag(name = "API Music Controller",
        description = "Контроллер для работы с информацией о музыке из внешнего API")
public class APIMusicController {

    private final APIMusicService apiMusicService;

    public APIMusicController(APIMusicService spotifyService) {
        this.apiMusicService = spotifyService;
    }

    /**
     * Получение информации об альбоме по его названию.
     *
     * @param albumName название альбома
     * @return ответ с информацией об альбоме
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает всю доступную информацию об альбоме")
    @GetMapping("/album")
    public ResponseEntity<Album> searchAlbumByName(@RequestParam("name") String albumName) {
        try {
            Album album = apiMusicService.getAlbumByName(albumName);
            if (album == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(album);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}