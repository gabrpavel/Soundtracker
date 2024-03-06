package com.soundtracker.backend.api;

import com.google.gson.Gson;
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
    private final Gson gson;
    private String accessToken;


    public SpotifyAPI() {
        okHttpClient = new OkHttpClient();
        gson = new Gson();
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
        String responseData = response.body().string();
        this.accessToken = responseData.substring(responseData.indexOf(":\"") + 2, responseData.indexOf("\","));
    }

    public String getArtistData(String artistId) throws IOException {
        if (accessToken == null) {
            fetchAccessToken();
        }

        Request request = new Request.Builder()
                .url(API_URL + "/artists/" + artistId)
                .get()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseData = response.body().string();
        //Artist artist = gson.fromJson(responseData, Artist.class);

        // Сохраняем данные в базе данных
        // artistRepository.save(artist);

        return "artist";
    }
}
