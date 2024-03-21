package com.soundtracker.backend.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soundtracker.backend.model.movie.Movie;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "directors")
@Schema(description = "Режиссер")
public class Director {

    @Id
    @Schema(description = "ID режиссера", example = "85365")
    private Long id;

    @Column(name = "ru_name")
    @Schema(description = "Имя режиссера на русском языке", example = "Акира Куросава")
    private String ruName;

    @Column(name = "en_name")
    @Schema(description = "Имя режиссера на английском языке", example = "Akira Kurosawa")
    private String enName;

    @Schema(description = "Фото режиссера", example = "https://www.kinopoisk.ru/name/85365/")
    @Column(name = "photo")
    private String photo;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "directors",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    private Set<Movie> movies = new HashSet<>();

    public Director(Long id, String ruName, String enName, String photo) {
        this.id = id;
        this.ruName = ruName;
        this.enName = enName;
        this.photo = photo;
    }
}
