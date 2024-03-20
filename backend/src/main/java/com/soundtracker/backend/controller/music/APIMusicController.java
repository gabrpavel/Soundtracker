package com.soundtracker.backend.controller.music;

import com.soundtracker.backend.service.music.APIMusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Контроллер для работы с музыкой из внешнего API
 */
@RestController
@RequestMapping("/api-soudtracker/api-music")
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
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает всю доступную информацию об альбоме")
    @GetMapping("/album")
    public ResponseEntity<String> searchAlbumByName(@RequestParam("name") String albumName) throws IOException {
        String response = apiMusicService.getAlbumByName(albumName);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}