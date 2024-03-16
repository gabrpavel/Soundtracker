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

    public ResponseEntity<?> getMovieInfoFromDatabase(String requestPath) {
        String databaseApiUrl = "http://localhost:8080/" + requestPath;
        return sendGetRequest(databaseApiUrl);
    }

    private ResponseEntity<?> sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return executeRequest(request);
    }

    private ResponseEntity<?> sendPostRequest(String url, String requestBody) {
        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        return executeRequest(request);
    }

    private ResponseEntity<?> executeRequest(Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return new ResponseEntity<>(response.body(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
