package com.soundtracker.backend.controller.music;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.service.music.DBMusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с музыкой из базы данных
 */
@RestController
@RequestMapping("/api-soudtracker/db-music")
@RequiredArgsConstructor
@Tag(name = "Database Music Controller",
        description = "Контроллер для работы с информацией о музыке из базы данных")
public class DBMusicController {

    private final DBMusicService dbMusicService;

    /**
     * Сохранение информации об альбоме.
     *
     * @param album объект альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Сохранение альбома", description = "Сохраняет информацию об альбоме в базу данных")
    @PostMapping("/save")
    public ResponseEntity<String> saveAlbum(@RequestBody Album album) throws JsonProcessingException {

        String response = dbMusicService.saveAlbum(album);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Удаление информации об альбоме.
     *
     * @param id идентификатор альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Удаление альбома", description = "Удаляет информацию об альбоме из базы данных")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAlbum(@RequestParam("id") String id) throws JsonProcessingException {
        String response = dbMusicService.deleteAlbum(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Обновление информации об альбоме.
     *
     * @param album объект альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Обновление альбома", description = "Обновляет информацию об альбоме в базе данных")
    @PutMapping("/update")
    public ResponseEntity<String> updateAlbum(@RequestBody Album album) throws JsonProcessingException {
        String response = dbMusicService.updateAlbum(album);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Получение информации об альбоме по его идентификатору.
     *
     * @param id идентификатор альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Поиск по ID", description = "Возвращает всю доступную информацию об альбоме")
    @GetMapping("/album-by-id")
    public ResponseEntity<String> getAlbumById(@RequestParam("id") String id) throws JsonProcessingException {
        String response = dbMusicService.getAlbumById(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * Получение информации об альбоме по его названию.
     *
     * @param name название альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает всю доступную информацию об альбоме")
    @GetMapping("/album-by-name")
    public ResponseEntity<String> getAlbumByName(@RequestParam("name") String name) throws JsonProcessingException {
        String response = dbMusicService.getAlbumByName(name);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
