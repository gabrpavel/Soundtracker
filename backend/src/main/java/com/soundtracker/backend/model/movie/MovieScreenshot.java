package com.soundtracker.backend.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie_screenshots")
public class MovieScreenshot {

    @Id
    private String id;
    private String url;
    private int height;
    private int width;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public MovieScreenshot(String id, String url, int height, int width) {
        this.id = id;
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public MovieScreenshot(String id, String url) {
        this.id = id;
        this.url = url;
    }
}
