package com.soundtracker.backend.service.music;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.model.music.Album;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервис для работы с музыкой
 */
@Service
public class MusicService {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public MusicService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.client = new OkHttpClient();
    }

    /**
     * Получение информации об альбоме из базы данных.
     *
     * @param name название альбома
     * @return информация об альбоме
     */
    public Optional<Album> getAlbumFromDatabase(String name) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-music/album-by-name?name=" + name;
        return getAlbum(databaseUrl);
    }

    /**
     * Получение информации об альбоме из внешнего API.
     *
     * @param name название альбома
     * @return информация об альбоме
     */
    public Optional<Album> getAlbumFromApi(String name) {
        String apiUrl = "http://localhost:8080/api-soudtracker/api-music/album?name=" + name;
        return getAlbum(apiUrl);
    }

    /**
     * Получение информации об альбоме по URL.
     *
     * @param url URL для получения информации об альбоме
     * @return информация об альбоме
     */
    @NotNull
    private Optional<Album> getAlbum(String url) {
        ResponseEntity<String> responseEntity = sendGetRequest(url);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            try {
                Album album = objectMapper.readValue(responseEntity.getBody(), Album.class);
                return Optional.of(album);
            } catch (JsonProcessingException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Обновление информации об альбоме в базе данных.
     *
     * @param album информация об альбоме
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> updatingAlbumResponse(Album album) {
        try {
            String albumJson = objectMapper.writeValueAsString(album);
            return updateAlbum(albumJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating album.");
        }
    }

    /**
     * Сохранение информации об альбоме.
     *
     * @param album информация об альбоме
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> saveAlbum(String album) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-music/save";
        return sendPostRequest(databaseUrl, album);
    }

    /**
     * Обновление информации об альбоме.
     *
     * @param albumJson информация об альбоме в формате JSON
     * @return ответ от сервера о результате обновления
     */
    public ResponseEntity<String> updateAlbum(String albumJson) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-music/update";
        return sendPutRequest(databaseUrl, albumJson);
    }

    /**
     * Отправка HTTP GET запроса к указанному URL
     *
     * @param url URL для отправки GET запроса
     * @return ответ от сервера
     */
    private ResponseEntity<String> sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return executeRequest(request);
    }

    /**
     * Отправка HTTP POST запроса с переданным телом запроса к указанному URL
     *
     * @param url         URL для отправки POST запроса
     * @param requestBody тело запроса
     * @return ответ от сервера
     */
    private ResponseEntity<String> sendPostRequest(String url, String requestBody) {
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return executeRequest(request);
    }

    /**
     * Отправка HTTP PUT запроса с переданным телом запроса к указанному URL
     *
     * @param url         URL для отправки PUT запроса
     * @param requestBody тело запроса
     * @return ответ от сервера
     */
    private ResponseEntity<String> sendPutRequest(String url, String requestBody) {
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return executeRequest(request);
    }

    /**
     * Выполнение HTTP запроса и обработка ответа
     *
     * @param request HTTP запрос
     * @return ответ от сервера
     */
    private ResponseEntity<String> executeRequest(Request request) {
        return getStringResponseEntity(request, client);
    }

    /**
     * Получение ответа от сервера в виде строки
     *
     * @param request HTTP запрос
     * @param client  клиент для отправки запроса
     * @return ответ от сервера в виде строки
     */
    @NotNull
    public static ResponseEntity<String> getStringResponseEntity(Request request, OkHttpClient client) {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return new ResponseEntity<>(response.body().string(), HttpStatusCode.valueOf(response.code()));
            } else {
                return new ResponseEntity<>(HttpStatusCode.valueOf(response.code()));
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}