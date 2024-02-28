package com.website.jdbc.model;

import lombok.Data;
import java.util.List;

@Data
public class Movie {

    Long id;
    String ruTitle;
    String enTitle;
    int releaseYear;
    String description;
    int length;
    String poster;

    List<Genre> genres;

    public Movie(Long id, String ruName, String enName, int releaseYear, String description, int length, String poster) {
        this.id = id;
        this.ruTitle = ruName;
        this.enTitle = enName;
        this.releaseYear = releaseYear;
        this.description = description;
        this.length = length;
        this.poster = poster;
    }
}
