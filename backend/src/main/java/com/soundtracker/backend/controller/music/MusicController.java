package com.soundtracker.backend.controller.music;

import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.service.music.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для работы с музыкой
 */
@RestController
@RequestMapping("/api-soudtracker/music")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://frontend:4200", "http://localhost:3000"})
@Tag(name = "Music Controller",
        description = "Контроллер для работы с информацией о музыке")
public class MusicController {

    private final MusicService musicService;

    /**
     * Получение информации об альбоме по его названию.
     *
     * @param name название альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает всю доступную информацию об альбоме")
    @GetMapping("/info")
    public ResponseEntity<Album> getAlbumInfo(@RequestParam("name") String name) {
        Optional<Album> albumOptional = musicService.getAlbumFromDatabase(name);
        if (albumOptional.isEmpty()) {
            albumOptional = musicService.getAlbumFromApi(name);
        }
        return albumOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
