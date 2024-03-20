package com.soundtracker.backend.controller.music;

import com.soundtracker.backend.service.music.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы с музыкой
 */
@RestController
@RequestMapping("/api-soudtracker/music")
@RequiredArgsConstructor
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
    public ResponseEntity<String> getAlbumInfo(@RequestParam("name") String name) {
        ResponseEntity<String> responseFromDatabase = musicService
                .getAlbumFromDatabase("/album-by-name?name=" + name);

        if (responseFromDatabase.getStatusCode().is2xxSuccessful() && responseFromDatabase.getBody() != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseFromDatabase.getBody());
        }

        // Если информация не найдена в базе данных, делаем запрос к внешнему API
        ResponseEntity<String> responseFromApi = musicService.getAlbumFromApi(name);
        if (responseFromApi.getStatusCode().is2xxSuccessful() && responseFromApi.getBody() != null) {
            ResponseEntity<String> response = musicService.savingAlbumResponse(responseFromApi.getBody());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } else {
            return ResponseEntity
                    .status(responseFromApi.getStatusCode())
                    .body("Error getting album info from database and API");
        }
    }
}
