package com.website.jdbc.dao;

import com.website.jdbc.model.Genre;
import com.website.jdbc.model.Movie;
import com.website.jdbc.utils.DBConnection;
import com.website.jdbc.utils.ToolsDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesDAO {

    private static final ToolsDB toolsDB = new ToolsDB();


    public List<Genre> getGenres(long movieId) {
        List<Genre> genres = new ArrayList<>();

        try {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setPs(toolsDB.getConn().prepareStatement("SELECT g.id, g.name " +
                    "FROM genres g " +
                    "JOIN movie_genres mg ON g.id = mg.genre_id " +
                    "WHERE mg.movie_id = ?"));
            toolsDB.getPs().setLong(1, movieId);
            toolsDB.setRs(toolsDB.getPs().executeQuery());

            while (toolsDB.getRs().next()) {
                Genre genre = new Genre(toolsDB.getRs().getLong("id"), toolsDB.getRs().getString("name"));
                genres.add(genre);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error while fetching genres for movie with ID: " + movieId, e);
        }

        return genres;
    }




    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try {
            toolsDB.setConn(DBConnection.getConnection());
            toolsDB.setSt(toolsDB.getConn().createStatement());
            toolsDB.setRs(toolsDB.getSt().executeQuery("SELECT id, ru_title, en_title, release_year, description, length FROM movies"));

            while (toolsDB.getRs().next()) {
                long movieId = toolsDB.getRs().getLong("id");
                Movie movie = new Movie(movieId,
                        toolsDB.getRs().getString("ru_title"),
                        toolsDB.getRs().getString("en_title"),
                        toolsDB.getRs().getInt("release_year"),
                        toolsDB.getRs().getString("description"),
                        toolsDB.getRs().getInt("length"));
                movie.setGenres(getGenres(movieId));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            toolsDB.closeDatabaseResources();
        }
        return movies;
    }

}
