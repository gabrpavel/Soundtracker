package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.model.movie.Movie;
import com.soundtracker.backend.model.music.Album;
import com.soundtracker.backend.repository.movie.MovieRepository;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.soundtracker.backend.service.music.MusicService.getStringResponseEntity;

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
    public MovieService(ObjectMapper objectMapper, MovieRepository movieRepository) {
        this.objectMapper = objectMapper;
        this.client = new OkHttpClient();
        this.movieRepository = movieRepository;
    }

    /**
     * Получение информации о кино из базы данных
     *
     * @param requestPath путь запроса
     * @return объект Movie, содержащий данные о кино
     */
    public Optional<Movie> getMovieInfoFromDatabase(String requestPath) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-movie" + requestPath;
        return getMovie(databaseUrl);
    }

    /**
     * Получение информации о кино из внешнего API
     *
     * @param id идентификатор кино
     * @return объект Movie, содержащий данные о кино
     */
    public Optional<Movie> getMovieInfoFromApi(Long id) {
        String apiUrl = "http://localhost:8080/api-soudtracker/api-movie/info?id=" + id;
        return getMovie(apiUrl);
    }

    /**
     * Получение кино по URL
     *
     * @param url URL для получения кино
     * @return объект Movie, содержащий данные о кино
     */
    @NotNull
    private Optional<Movie> getMovie(String url) {
        ResponseEntity<String> responseEntity = sendGetRequest(url);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            try {
                Movie movie = objectMapper.readValue(responseEntity.getBody(), Movie.class);
                return Optional.of(movie);
            } catch (JsonProcessingException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Отправка HTTP GET запроса к указанному URL
     *
     * @param url URL для отправки GET запроса
     * @return ответ от сервера
     */
    private ResponseEntity<String> sendGetRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        return executeRequest(request);
    }

    /**
     * Обновление информации о кино
     *
     * @param movie информация о кино
     * @return ответ от сервера о результате обновления
     */
    public ResponseEntity<String> updatingMovieResponse(Movie movie) {
        try {
            String movieJson = objectMapper.writeValueAsString(movie);
            return updateMovie(movieJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating movie.");
        }
    }

    /**
     * Обновление информации о кино
     *
     * @param movieJson информация о кино в формате JSON
     * @return ответ от сервера о результате обновления
     */
    public ResponseEntity<String> updateMovie(String movieJson) {
        String databaseUrl = "http://localhost:8080/api-soudtracker/db-movie/update";
        return sendPutRequest(databaseUrl, movieJson);
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
     * Отправка HTTP запроса
     *
     * @param request запрос
     * @return ответ от сервера
     */
    private ResponseEntity<String> executeRequest(Request request) {
        return getStringResponseEntity(request, client);
    }

    /**
     * Установка альбома для кино
     *
     * @param id идентификатор кино
     * @return ответ от сервера о результате установки альбома
     */
    public ResponseEntity<String> setAlbum(Long id, String albumName) throws JsonProcessingException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            String url = "http://localhost:8080/api-soudtracker/api-music/album?name=" + albumName;
            ResponseEntity<String> responseEntity = sendGetRequest(url);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String albumJson = responseEntity.getBody();
                Album album = objectMapper.readValue(albumJson, Album.class);
                movie.setAlbum(album);
                movieRepository.save(movie);
                return ResponseEntity.ok("Album set.");
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body("Error getting album.");
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with id: " + id + " not found.");
    }
}