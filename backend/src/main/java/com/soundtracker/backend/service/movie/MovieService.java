package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.repository.movie.MovieRepository;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Сервис для работы с кино
 */
@Service
public class MovieService {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final MovieRepository movieRepository;

    /**
     * Конструктор по умолчанию для инициализации клиента OkHttp
     */
    public MovieService(ObjectMapper objectMapper,
                        MovieRepository movieRepository) {
        this.objectMapper = objectMapper;
        this.client = new OkHttpClient();
        this.movieRepository = movieRepository;
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
    public ResponseEntity<String> savingMovieResponse(String movie) {
        ResponseEntity<String> savedMovieResponse = saveMovie(movie);
        if (savedMovieResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(savedMovieResponse.getBody());
        } else {
            return ResponseEntity.status(savedMovieResponse.getStatusCode()).body("Error saving movie to database");
        }
    }

    /**
     * Обработка ответа от внешнего API с информацией о кино
     *
     * @param movie информация о кино
     * @return ответ от сервера с информацией о кино
     */
    public ResponseEntity<String> updatingMovieResponse(String movie) {
        ResponseEntity<String> updatedMovieResponse = updateMovie(movie);
        if (updatedMovieResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(updatedMovieResponse.getBody());
        } else {
            return ResponseEntity.status(updatedMovieResponse.getStatusCode()).body("Error updating movie in database");
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
     * Обновление информации о кино в базе данных
     *
     * @param movie информация о кино
     * @return ответ от сервера о результате обновления
     */
    public ResponseEntity<String> updateMovie(String movie) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-movie/update";
        return sendPutRequest(databaseUrl, movie);
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

    /**
     * Установка альбома для кино
     *
     * @param id идентификатор кино
     * @return ответ от сервера о результате установки альбома
     */
    public ResponseEntity<String> setAlbum(Long id) throws JsonProcessingException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            String url = "http://localhost:8080/api-soudtracker/music/info?name=" + movie.getEnTitle();
            ResponseEntity<String> responseEntity = sendGetRequest(url);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String albumJson = responseEntity.getBody();
                Album album = objectMapper.readValue(albumJson, Album.class);
                movie.setAlbum(album);
                movieRepository.save(movie);
                return ResponseEntity.ok("Album set");
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body("Error getting album");
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
    }
}
