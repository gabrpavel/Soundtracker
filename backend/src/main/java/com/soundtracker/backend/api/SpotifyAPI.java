package com.soundtracker.backend.api;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class SpotifyAPI {

    @Value("${spotify.client.secret}")
    String clientSecret;

    @Value("${spotify.client.id}")
    String clientId;

    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String API_URL = "https://api.spotify.com/v1";
    private final OkHttpClient okHttpClient;
    private String accessToken;


    public SpotifyAPI() {
        okHttpClient = new OkHttpClient();
    }

    private void fetchAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBody = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseData = Objects.requireNonNull(response.body()).string();
        this.accessToken = responseData.substring(responseData.indexOf(":\"") + 2, responseData.indexOf("\","));
    }

    public String getArtistData(String artistId) throws IOException {
        if (accessToken == null) {
            fetchAccessToken();
        }
        String url = API_URL + "/artists/" + artistId;
        return makeRequest(url);
    }

    private String makeRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        return Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
    }
}
