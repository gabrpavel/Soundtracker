package com.soundtracker.backend.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Класс для взаимодействия с Kinopoisk API
 */
@Component
public class KinopoiskAPI {

    @Value(value = "${kinopoisk.apiKey}")
    String apiKey;

    private static final String API_URL = "https://api.kinopoisk.dev/v1.4/";
    private final OkHttpClient okHttpClient;

    /**
     * Конструктор по умолчанию для инициализации клиента OkHttpClient
     */
    public KinopoiskAPI() {
        this.okHttpClient = new OkHttpClient();
    }

    /**
     * Создает и выполняет HTTP запрос к Kinopoisk API
     *
     * @param url URL для выполнения запроса
     * @return ответ от сервера в формате JSON
     * @throws IOException если возникают проблемы при выполнении запроса
     */
    private String makeRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", apiKey)
                .build();
        return Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
    }

    /**
     * Создает HTTP запрос к Kinopoisk API для получения информации о кино по его идентификатору
     *
     * @param id идентификатор кино
     * @return ответ от сервера в формате JSON с данными о кино
     * @throws IOException если возникают проблемы при выполнении запроса
     */
    public String searchMovieById(Long id) throws IOException {
        String url = API_URL + "movie/" + id;
        return makeRequest(url);
    }

    /**
     * Создает HTTP запрос к Kinopoisk API для поиска кино по его названию
     *
     * @param title название кино
     * @return ответ от сервера в формате JSON с данными о найденном кино
     * @throws IOException если возникают проблемы при выполнении запроса
     */
    public String searchMovieByTitle(String title) throws IOException {
        String url = API_URL + "movie/search?page=1&limit=1&query=" + title;
        return makeRequest(url);
    }

    /**
     * Создает HTTP запрос к Kinopoisk API для получения информации о кино по его идентификатору
     *
     * @param id идентификатор кино
     * @return ответ от сервера в формате JSON с данными о кино
     * @throws IOException если возникают проблемы при выполнении запроса
     */
    public String searchScreenshotsByMovieId(Long id) throws IOException {
        String url = API_URL + "image?page=1&limit=40&selectFields=movieId" +
                "&selectFields=url&selectFields=height&selectFields=width" +
                "&notNullFields=movieId&notNullFields=url" +
                "&notNullFields=height&notNullFields=width&movieId="+ id +"&type=screenshot";
        return makeRequest(url);
    }
}