package com.soundtracker.backend.service.music;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.model.music.Track;
import com.soundtracker.backend.repository.music.AlbumRepository;
import com.soundtracker.backend.repository.music.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Сервис для работы с музыкой из базы данных
 */
@Service
@RequiredArgsConstructor
public class DBMusicService {

    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final ObjectMapper objectMapper;

    /**
     * Сохранение информации об альбоме.
     *
     * @param album объект альбома
     * @return строковое представление JSON сохраненного альбома
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String saveAlbum(Album album) throws JsonProcessingException {
        Set<Track> tracks = album.getTracks();
        tracks.forEach(track -> {
            if (!trackRepository.existsById(track.getId())) {
                trackRepository.save(track);
            }
        });
        album.setTracks(tracks);
        albumRepository.save(album);
        return objectMapper.writeValueAsString(album);
    }

    /**
     * Удаление информации об альбоме.
     *
     * @param id идентификатор альбома
     * @return строковое представление JSON идентификатора удаленного альбома
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String deleteAlbum(String id) throws JsonProcessingException {
        albumRepository.deleteById(id);
        return objectMapper.writeValueAsString(id);
    }

    /**
     * Обновление информации об альбоме.
     *
     * @param album объект альбома
     * @return строковое представление JSON обновленного альбома
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String updateAlbum(Album album) throws JsonProcessingException {
        albumRepository.save(album);
        return objectMapper.writeValueAsString(album);
    }

    /**
     * Получение информации об альбоме по его идентификатору.
     *
     * @param id идентификатор альбома
     * @return строковое представление JSON найденного альбома, если он существует; в противном случае null
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String getAlbumById(String id) throws JsonProcessingException {
        Optional<Album> optionalAlbum = albumRepository.findById(id);
        if (optionalAlbum.isPresent()) {
            Album album = optionalAlbum.get();
            return objectMapper.writeValueAsString(album);
        } else {
            return null;
        }
    }

    /**
     * Получение информации об альбоме по его названию.
     *
     * @param name название альбома
     * @return строковое представление JSON найденного альбома, если он существует; в противном случае null
     * @throws JsonProcessingException если возникают проблемы при преобразовании в JSON
     */
    public String getAlbumByName(String name) throws JsonProcessingException {
        Optional<Album> optionalAlbum = albumRepository.findByName(name);
        if (optionalAlbum.isPresent()) {
            Album album = optionalAlbum.get();
            return objectMapper.writeValueAsString(album);
        } else {
            return null;
        }
    }
}
