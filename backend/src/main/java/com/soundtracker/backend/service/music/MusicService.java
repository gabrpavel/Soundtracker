package com.soundtracker.backend.service.music;

import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Сервис для работы с музыкой
 */
@Service
public class MusicService {

    private final OkHttpClient client;

    public MusicService() {
        this.client = new OkHttpClient();
    }

    /**
     * Получение информации об альбоме из базы данных.
     *
     * @param requestPath путь запроса
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> getAlbumFromDatabase(String requestPath) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-music" + requestPath;
        return sendGetRequest(databaseUrl);
    }

    /**
     * Получение информации об альбоме из внешнего API.
     *
     * @param name название альбома
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> getAlbumFromApi(String name) {
        String apiUrl = "http://localhost:8080/api-soudtracker/api-music/album?name=" + name;
        return sendGetRequest(apiUrl);
    }

    /**
     * Сохранение информации об альбоме в базу данных.
     *
     * @param album информация об альбоме
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> savingAlbumResponse(String album) {
        ResponseEntity<String> savedAlbumResponse = saveAlbum(album);
        if (savedAlbumResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(savedAlbumResponse.getBody());
        } else {
            return ResponseEntity.status(savedAlbumResponse.getStatusCode()).body("Error saving album to database");
        }
    }

    /**
     * Обновление информации об альбоме в базе данных.
     *
     * @param album информация об альбоме
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> updatingAlbumResponse(String album) {
        ResponseEntity<String> updatedAlbumResponse = updateAlbum(album);
        if (updatedAlbumResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(updatedAlbumResponse.getBody());
        } else {
            return ResponseEntity.status(updatedAlbumResponse.getStatusCode()).body("Error updating album in database");
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
     * @param album информация об альбоме
     * @return ответ с информацией об альбоме в формате JSON
     */
    public ResponseEntity<String> updateAlbum(String album) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-music/update";
        return sendPutRequest(databaseUrl, album);
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
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return new ResponseEntity<>(response.body().string(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
