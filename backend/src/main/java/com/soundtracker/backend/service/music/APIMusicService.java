package com.soundtracker.backend.service.music;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.api.SpotifyAPI;
import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.model.music.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Сервис для работы с музыкой из внешнего API (Spotify API)
 */
@Service
@RequiredArgsConstructor
public class APIMusicService {

    private final SpotifyAPI spotifyAPI;
    private final ObjectMapper objectMapper;

    /**
     * Поиск альбома по его названию
     *
     * @param albumName название альбома
     * @return строковое представление JSON найденного альбома
     * @throws IOException если возникают проблемы при чтении ответа от внешнего API
     */
    public String searchAlbumByName(String albumName) throws IOException {
        return spotifyAPI.searchAlbumByName(albumName);
    }

    /**
     * Получение альбома по его названию
     *
     * @param albumName название альбома
     * @return объект Album, содержащий информацию об альбоме
     * @throws IOException если возникают проблемы при чтении ответа от внешнего API
     */
    public Album getAlbumByName(String albumName) throws IOException {
        String responseBody = searchAlbumByName(albumName);
        if (responseBody.isEmpty()) {
            return null;
        }
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        jsonNode = jsonNode.get("albums").get("items").get(0);
        Album album = new Album();
        album.setId(jsonNode.get("id").asText());
        album.setArtistName(jsonNode.get("artists").get(0).get("name").asText());
        album.setCover(jsonNode.get("images").get(0).get("url").asText());
        album.setName(jsonNode.get("name").asText());
        album.setTotalTracks(jsonNode.get("total_tracks").asInt());
        album.setSpotifyUrl(jsonNode.get("external_urls").get("spotify").asText());
        album.setTracks(getAlbumTracks(album.getId()));

        return album;
    }

    /**
     * Получение списка треков альбома по его идентификатору
     *
     * @param albumID идентификатор альбома
     * @return множество объектов Track, содержащих информацию о треках альбома
     * @throws IOException если возникают проблемы при чтении ответа от внешнего API
     */
    public Set<Track> getAlbumTracks(String albumID) throws IOException {
        String responseBody = spotifyAPI.getAlbumTracks(albumID);
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Set<Track> tracks = new HashSet<>();
        for (JsonNode trackNode : jsonNode.get("items")) {
            Track track = new Track();
            track.setId(trackNode.get("id").asText());
            track.setArtistName(trackNode.get("artists").get(0).get("name").asText());
            track.setName(trackNode.get("name").asText());
            track.setPreviewUrl(trackNode.get("preview_url").asText());
            track.setTrackNumber(trackNode.get("track_number").asInt());
            track.setSpotifyUrl(trackNode.get("external_urls").get("spotify").asText());
            tracks.add(track);
        }
        return tracks;
    }
}
