package com.soundtracker.backend.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_types")
public class MovieType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID типа кино", example = "4")
    private Long id;

    @Column(name = "type")
    @Schema(description = "Название типа кино", example = "anime")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Movie> movies;

    public MovieType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
