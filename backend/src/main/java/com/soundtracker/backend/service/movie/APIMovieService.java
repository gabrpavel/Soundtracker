package com.soundtracker.backend.service.movie;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundtracker.backend.api.KinopoiskAPI;
import com.soundtracker.backend.model.movie.*;
import com.soundtracker.backend.repository.movie.GenreRepository;
import com.soundtracker.backend.repository.movie.MovieTypeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Сервис для работы с кино из внешнего API (Kinopoisk API)
 */
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

    /**
     * Получение данных о кино по его названию из внешнего API (Kinopoisk API)
     *
     * @param title название кино
     * @return объект Movie, содержащий данные о кино
     * @throws IOException если возникают проблемы при чтении ответа от внешнего API
     */
    public Movie searchMovieByTitle(String title) throws IOException {
        String responseBody = kinopoiskAPI.searchMovieByTitle(title);
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        jsonNode = jsonNode.get("docs").get(0);

        Movie movie = getMovieDetailsFromJson(jsonNode);
        movie.setGenres(getGenresFromJson(jsonNode));
        movie.setType(getMovieTypeFromJson(jsonNode));

        return movie;
    }

    /**
     * Получение подробных данных о кино по его идентификатору из внешнего API (Kinopoisk API)
     *
     * @param id идентификатор фильма
     * @return строковое представление JSON сохраненного фильма
     * @throws IOException если возникают проблемы при чтении ответа от внешнего API
     */
    public Movie getMovieDetails(Long id) throws IOException {
        String responseBody = kinopoiskAPI.searchMovieById(id);
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Movie movie = getMovieDetailsFromJson(jsonNode);
        movie.setGenres(getGenresFromJson(jsonNode));
        movie.setActors(getActorsFromJson(jsonNode));
        movie.setDirectors(getDirectorsFromJson(jsonNode));
        movie.setType(getMovieTypeFromJson(jsonNode));
        movie.setMovieScreenshots(getMovieScreenshotsFromJson(id));

        return movie;
    }

    /**
     * Создание объекта Movie из JSON-узла с данными о кино
     *
     * @param jsonNode JSON-узел с данными о кино
     * @return объект Movie с данными о кино
     */
    public Movie getMovieDetailsFromJson(JsonNode jsonNode) {
        return new Movie(jsonNode.get("id").asLong(), jsonNode.get("name").asText(),
                jsonNode.get("alternativeName").asText(), jsonNode.get("year").asInt(),
                jsonNode.get("description").asText(), jsonNode.get("movieLength").asInt(),
                jsonNode.get("poster").get("url").asText());
    }

    /**
     * Получение списка жанров из JSON-узла с данными о кино
     *
     * @param jsonNode JSON-узел с данными о кино
     * @return список жанров
     */
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

    /**
     * Получение списка актеров из JSON-узла с данными о кино
     *
     * @param jsonNode JSON-узел с данными о кино
     * @return список актеров
     */
    public Set<Actor> getActorsFromJson(JsonNode jsonNode) {
        JsonNode actorsNode = jsonNode.get("persons");
        Set<Actor> actors = new HashSet<>();
        for (JsonNode actorNode : actorsNode) {
            if (actorNode.get("enProfession").asText().equals("actor")) {
                Long actorId = actorNode.get("id").asLong();
                String firstName = actorNode.get("name").asText();
                String lastName = actorNode.get("enName").asText();
                String photo = actorNode.get("photo").asText();
                Actor actor = new Actor(actorId, firstName, lastName, photo);
                actors.add(actor);
            }
        }
        return actors;
    }

    /**
     * Получение списка режиссеров из JSON-узла с данными о кино
     *
     * @param jsonNode JSON-узел с данными о кино
     * @return список режиссеров
     */
    public Set<Director> getDirectorsFromJson(JsonNode jsonNode) {
        JsonNode directorsNode = jsonNode.get("persons");
        Set<Director> directors = new HashSet<>();
        for (JsonNode directorNode : directorsNode) {
            if (directorNode.get("enProfession").asText().equals("director")) {
                Long directorId = directorNode.get("id").asLong();
                String firstName = directorNode.get("name").asText();
                String lastName = directorNode.get("enName").asText();
                String photo = directorNode.get("photo").asText();
                Director director = new Director(directorId, firstName, lastName, photo);
                directors.add(director);
            }
        }
        return directors;
    }

    /**
     * Получение типа кино из JSON-узла с данными о кино
     *
     * @param jsonNode JSON-узел с данными о кино
     * @return объект MovieType с данными о типе кино
     */
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

    /**
     * Получение скриншотов кино по его идентификатору из внешнего API (Kinopoisk API)
     *
     * @param id идентификатор кино
     * @return список объектов Image с данными о скриншотах кино
     * @throws IOException если возникают проблемы при чтении ответа от внешнего API
     */
    public Set<MovieScreenshot> getMovieScreenshotsFromJson(Long id) throws IOException {
        Set<MovieScreenshot> movieScreenshots = new HashSet<>();
        String responseBody = kinopoiskAPI.searchScreenshotsByMovieId(id);
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        JsonNode imagesNode = jsonNode.get("docs");
        if (imagesNode != null) {
            for (JsonNode imageNode : imagesNode) {
                JsonNode urlNode = imageNode.get("url");
                JsonNode heightNode = imageNode.get("height");
                JsonNode widthNode = imageNode.get("width");
                JsonNode idNode = imageNode.get("id");

                if (urlNode != null && heightNode != null && widthNode != null && idNode != null) {
                    String url = urlNode.asText();
                    int height = heightNode.asInt();
                    int width = widthNode.asInt();
                    String imageId = idNode.asText();
                    MovieScreenshot movieScreenshot = new MovieScreenshot(imageId, url, height, width);
                    movieScreenshots.add(movieScreenshot);
                }
            }
        }
        return movieScreenshots;
    }
}
