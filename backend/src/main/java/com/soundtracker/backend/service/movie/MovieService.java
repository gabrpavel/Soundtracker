package com.soundtracker.backend.service.movie;

import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Сервис для работы с кино
 */
@Service
public class MovieService {

    private final OkHttpClient client;

    /**
     * Конструктор по умолчанию для инициализации клиента OkHttp
     */
    public MovieService() {
        this.client = new OkHttpClient();
    }

    /**
     * Получение информации о кино из базы данных по указанному пути запроса
     *
     * @param requestPath путь запроса к базе данных
     * @return ответ от сервера с информацией о кино
     */
    public ResponseEntity<String> getMovieInfoFromDatabase(String requestPath) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-movie" + requestPath;
        return sendGetRequest(databaseUrl);
    }

    /**
     * Получение информации о кино из внешнего API по его идентификатору
     *
     * @param id идентификатор кино
     * @return ответ от сервера с информацией о кино
     */
    public ResponseEntity<String> getMovieInfoFromApi(Long id) {
        String apiUrl = "http://localhost:8080/api-soudtracker/api-movie/info?id=" + id;
        return sendGetRequest(apiUrl);
    }

    /**
     * Обработка ответа от внешнего API с информацией о кино
     *
     * @param movie информация о кино
     * @return ответ от сервера с информацией о кино
     */
    public ResponseEntity<String> processApiResponse(String movie) {
        ResponseEntity<String> savedMovieResponse = saveMovie(movie);
        if (savedMovieResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(savedMovieResponse.getBody());
        } else {
            return ResponseEntity.status(savedMovieResponse.getStatusCode()).body("Error saving movie to database");
        }
    }

    /**
     * Сохранение информации о кино в базу данных
     *
     * @param movie информация о кино
     * @return ответ от сервера о результате сохранения
     */
    public ResponseEntity<String> saveMovie(String movie) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-movie/save";
        return sendPostRequest(databaseUrl, movie);
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
