package com.soundtracker.backend.api;

import com.google.gson.Gson;
import com.soundtracker.backend.dto.response.movie.MovieDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private final Gson gson;

    public KinopoiskAPI() {
        this.okHttpClient = new OkHttpClient();
        gson = new Gson();
    }

    public MovieDto searchMovie(String query) throws IOException {
        Request request = new Request.Builder()
                .url(API_URL + "movie/" + query)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", apiKey)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseData = response.body().string();
        return gson.fromJson(responseData, MovieDto.class);
    }
}