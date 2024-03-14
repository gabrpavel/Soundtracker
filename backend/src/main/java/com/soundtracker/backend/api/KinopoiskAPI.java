package com.soundtracker.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.dto.response.movie.MovieDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KinopoiskAPI {

    @Value(value = "${kinopoisk.apiKey}")
    String apiKey;

    private static final String API_URL = "https://api.kinopoisk.dev/v1.4/";
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    public KinopoiskAPI(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.okHttpClient = new OkHttpClient();
    }

    public MovieDto searchMovie(Long id) throws IOException {
        Request request = new Request.Builder()
                .url(API_URL + "movie/" + id)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", apiKey)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseBody = response.body() != null ? response.body().string() : null;
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return new MovieDto(jsonNode.get("id").asLong(), jsonNode.get("name").asText(),
                jsonNode.get("alternativeName").asText(), jsonNode.get("enName").asText(),
                jsonNode.get("type").asText(), jsonNode.get("typeNumber").asInt(),
                jsonNode.get("year").asInt(), jsonNode.get("description").asText(),
                jsonNode.get("movieLength").asInt());
    }
}