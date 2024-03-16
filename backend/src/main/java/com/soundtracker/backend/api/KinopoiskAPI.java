package com.soundtracker.backend.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class KinopoiskAPI {

    @Value(value = "${kinopoisk.apiKey}")
    String apiKey;

    private static final String API_URL = "https://api.kinopoisk.dev/v1.4/";
    private final OkHttpClient okHttpClient;

    public KinopoiskAPI() {
        this.okHttpClient = new OkHttpClient();
    }

    private String makeRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", apiKey)
                .build();

        return Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
    }

    public String searchMovieById(Long id) throws IOException {

        String url = API_URL + "movie/" + id;
        return makeRequest(url);
    }

    public String searchMovieByTitle(String title) throws IOException {

        String url = API_URL + "movie/search?page=1&limit=1&query=" + title;
        return makeRequest(url);
    }
}