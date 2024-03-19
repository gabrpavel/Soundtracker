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
@Table(name = "images")
public class Image {

    @Id
    String id;
    String url;
    String type;

    int height;
    int width;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Image(String id, String url, String type, int height, int width) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.height = height;
        this.width = width;
    }
}
