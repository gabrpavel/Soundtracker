package com.soundtracker.backend.controller.music;

import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.service.music.DBMusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с музыкой из базы данных
 */
@RestController
@RequestMapping("/api-soudtracker/db-music")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://frontend:4200", "http://localhost:3000"})
@Tag(name = "Database Music Controller",
        description = "Контроллер для работы с информацией о музыке из базы данных")
public class DBMusicController {

    private final DBMusicService dbMusicService;

    /**
     * Сохранение альбома
     *
     * @param album объект альбома
     * @return ответ о результате сохранения альбома
     */
    @Operation(summary = "Сохранение альбома", description = "Сохраняет информацию об альбоме в базу данных")
    @PostMapping("/save")
    public ResponseEntity<String> saveAlbum(@RequestBody Album album) {
        try {
            dbMusicService.saveAlbum(album);
            return ResponseEntity.status(HttpStatus.OK).body("Album saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving album.");
        }
    }

    /**
     * Удаление альбома
     *
     * @param id идентификатор альбома
     * @return ответ о результате удаления альбома
     */
    @Operation(summary = "Удаление альбома", description = "Удаляет информацию об альбоме из базы данных")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAlbum(@RequestParam("id") String id) {
        try {
            String response = dbMusicService.deleteAlbum(id);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Album with id: " + id + " doesn't exist.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting album.");
        }
    }

    /**
     * Обновление альбома
     *
     * @param album объект альбома
     * @return ответ о результате обновления альбома
     */
    @Operation(summary = "Обновление альбома", description = "Обновляет информацию об альбоме в базе данных")
    @PutMapping("/update")
    public ResponseEntity<String> updateAlbum(@RequestBody Album album) {
        try {
            dbMusicService.saveAlbum(album);
            return ResponseEntity.status(HttpStatus.OK).body("Album updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating album.");
        }
    }

    /**
     * Поиск по ID
     *
     * @param id идентификатор альбома
     * @return информация об альбоме
     */
    @Operation(summary = "Поиск по ID", description = "Возвращает информацию об альбоме")
    @GetMapping("/album-by-id")
    public ResponseEntity<Album> getAlbumById(@RequestParam("id") String id) {
        try {
            Album response = dbMusicService.getAlbumById(id);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Поиск по названию
     *
     * @param name название альбома
     * @return информация об альбоме
     */
    @Operation(summary = "Поиск по названию", description = "Возвращает информацию об альбоме")
    @GetMapping("/album-by-name")
    public ResponseEntity<Album> getAlbumByName(@RequestParam("name") String name) {
        try {
            Album response = dbMusicService.getAlbumByName(name);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}