package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.api.KinopoiskAPI;
import com.soundtracker.backend.model.*;
import com.soundtracker.backend.repository.GenreRepository;
import com.soundtracker.backend.repository.MovieTypeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class APIMovieService {

    private final KinopoiskAPI kinopoiskAPI;
    private final GenreRepository genreRepository;
    private final MovieTypeRepository movieTypeRepository;
    private final ObjectMapper objectMapper;

    public APIMovieService(KinopoiskAPI kinopoiskAPI, GenreRepository genreRepository, MovieTypeRepository movieTypeRepository, ObjectMapper objectMapper) {
        this.kinopoiskAPI = kinopoiskAPI;
        this.genreRepository = genreRepository;
        this.movieTypeRepository = movieTypeRepository;
        this.objectMapper = objectMapper;
    }

    public Movie getMovieInfo(String title) throws IOException {

        String responseBody = kinopoiskAPI.searchMovieByTitle(title);
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        jsonNode = jsonNode.get("docs").get(0);

        Movie movie = getMovieDetailsFromJson(jsonNode);
        movie.setGenres(getGenresFromJson(jsonNode));
        movie.setType(getMovieTypeFromJson(jsonNode));

        return movie;
    }

    public String getMovieDetails(Long id) throws IOException {

        String responseBody = kinopoiskAPI.searchMovieById(id);
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Movie movie = getMovieDetailsFromJson(jsonNode);
        movie.setGenres(getGenresFromJson(jsonNode));
        movie.setActors(getActorsFromJson(jsonNode));
        movie.setDirectors(getDirectorsFromJson(jsonNode));
        movie.setType(getMovieTypeFromJson(jsonNode));

        return objectMapper.writeValueAsString(movie);
    }
    public Movie getMovieDetailsFromJson(JsonNode jsonNode) {
        return new Movie(jsonNode.get("id").asLong(), jsonNode.get("name").asText(),
                jsonNode.get("alternativeName").asText(), jsonNode.get("year").asInt(),
                jsonNode.get("description").asText(), jsonNode.get("movieLength").asInt(),
                jsonNode.get("poster").get("url").asText());
    }

    public Set<Genre> getGenresFromJson(JsonNode jsonNode) {
        Set<Genre> genres = new HashSet<>();
        JsonNode genresNode = jsonNode.get("genres");
        if (genresNode != null) {
            for (JsonNode genreNode : genresNode) {
                String genreName = genreNode.get("name").asText();
                Optional<Genre> existingGenre = genreRepository.findByName(genreName);
                if (existingGenre.isPresent()) {
                    genres.add(existingGenre.get());
                } else {
                    Genre newGenre = new Genre();
                    newGenre.setName(genreName);
                    genres.add(genreRepository.save(newGenre));
                }
            }
        }
        return genres;
    }





    public Set<Actor> getActorsFromJson(JsonNode jsonNode) {
        JsonNode actorsNode = jsonNode.get("persons");
        Set<Actor> actors = new HashSet<>();
        for (JsonNode actorNode : actorsNode) {
            if (actorNode.get("enProfession").asText().equals("actor")) {
                Long actorId = actorNode.get("id").asLong();
                String firstName = actorNode.get("name").asText();
                String lastName = actorNode.get("enName").asText();
                Actor actor = new Actor(actorId, firstName, lastName);
                actors.add(actor);
            }
        }
        return actors;
    }

    public Set<Director> getDirectorsFromJson(JsonNode jsonNode) {
        JsonNode directorsNode = jsonNode.get("persons");
        Set<Director> directors = new HashSet<>();
        for (JsonNode directorNode : directorsNode) {
            if (directorNode.get("enProfession").asText().equals("director")) {
                Long directorId = directorNode.get("id").asLong();
                String firstName = directorNode.get("name").asText();
                String lastName = directorNode.get("enName").asText();
                Director director = new Director(directorId, firstName, lastName);
                directors.add(director);
            }
        }
        return directors;
    }

    public MovieType getMovieTypeFromJson(JsonNode jsonNode) {
        String typeName = jsonNode.get("type").asText();
        Optional<MovieType> existingType = movieTypeRepository.findByName(typeName);
        if (existingType.isPresent()) {
            return existingType.get();
        } else {
            MovieType newMovieType = new MovieType();
            newMovieType.setName(typeName);
            return movieTypeRepository.save(newMovieType);
        }
    }
}
