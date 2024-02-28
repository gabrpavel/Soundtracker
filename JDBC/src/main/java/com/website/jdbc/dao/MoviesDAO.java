package com.website.jdbc.dao;

import com.website.jdbc.model.Genre;
import com.website.jdbc.model.Movie;
import com.website.jdbc.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesDAO {

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, ru_title, en_title, release_year, description, length, poster FROM movies")) {

            while (rs.next()) {
                long movieId = rs.getLong("id");
                Movie movie = new Movie(movieId,
                        rs.getString("ru_title"),
                        rs.getString("en_title"),
                        rs.getInt("release_year"),
                        rs.getString("description"),
                        rs.getInt("length"),
                        rs.getString("poster"));
                movie.setGenres(getGenres(movieId));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return movies;
    }

    public List<Genre> getGenres(long movieId) {
        List<Genre> genres = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT g.id, g.name " +
                     "FROM genres g " +
                     "JOIN movie_genres mg ON g.id = mg.genre_id " +
                     "WHERE mg.movie_id = ?")) {

            ps.setLong(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre(rs.getLong("id"), rs.getString("name"));
                    genres.add(genre);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error while fetching genres for movie with ID: " + movieId, e);
        }

        return genres;
    }


}
