package com.soundtracker.backend.service.movie;

import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MovieService {

    private final OkHttpClient client;

    public MovieService() {
        this.client = new OkHttpClient();
    }

    public ResponseEntity<String> getMovieInfoFromDatabase(String requestPath) {

        String databaseApiUrl = "http://localhost:8080/api-soudtracker/db-movie" + requestPath;
        return sendGetRequest(databaseApiUrl);
    }

    public ResponseEntity<String> getMovieInfoFromApi(Long id) {

        String apiServiceApiUrl = "http://localhost:8080/api-soudtracker/api-movie/details?id=" + id;
        return sendGetRequest(apiServiceApiUrl);
    }

    public ResponseEntity<String> processApiResponse(String movie) {

        ResponseEntity<String> savedMovieResponse = saveMovie(movie);
        if (savedMovieResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(savedMovieResponse.getBody());
        } else {
            return ResponseEntity.status(savedMovieResponse.getStatusCode()).body("Error saving movie to database");
        }
    }

    public ResponseEntity<String> saveMovie(String movie) {

        String databaseApiUrl = "http://localhost:8080/api-soudtracker/db-movie/save";
        return sendPostRequest(databaseApiUrl, movie);
    }

    private ResponseEntity<String> sendGetRequest(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        return executeRequest(request);
    }

    private ResponseEntity<String> sendPostRequest(String url, String requestBody) {

        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return executeRequest(request);
    }

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
