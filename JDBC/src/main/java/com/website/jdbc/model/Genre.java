package com.website.jdbc.model;

import lombok.Data;

@Data
public class Genre {

    Long id;
    String name;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}