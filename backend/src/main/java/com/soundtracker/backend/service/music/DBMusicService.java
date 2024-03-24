package com.soundtracker.backend.service.music;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.model.music.Track;
import com.soundtracker.backend.repository.music.AlbumRepository;
import com.soundtracker.backend.repository.music.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     */
    @Transactional
    public void saveAlbum(Album album) {
        Set<Track> tracks = album.getTracks();
        tracks.forEach(track -> {
            if (!trackRepository.existsById(track.getId())) {
                trackRepository.save(track);
            }
        });
        album.setTracks(tracks);
        albumRepository.save(album);
    }

    /**
     * Удаление альбома.
     *
     * @param id идентификатор альбома
     * @return ответ о результате удаления альбома
     */
    @Transactional
    public String deleteAlbum(String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            trackRepository.deleteAll(album.get().getTracks());
            albumRepository.deleteById(id);
            return "Album with id " + id + " was deleted";
        }
        return null;
    }

    /**
     * Получение альбома по его идентификатору.
     *
     * @param id идентификатор альбома
     * @return альбом
     */
    public Album getAlbumById(String id) {
        Optional<Album> optionalAlbum = albumRepository.findById(id);
        return optionalAlbum.orElse(null);
    }

    /**
     * Получение альбома по его названию.
     *
     * @param name название альбома
     * @return альбом
     */
    public Album getAlbumByName(String name) {
        Optional<Album> optionalAlbum = albumRepository.findByName(name);
        return optionalAlbum.orElse(null);
    }
}
