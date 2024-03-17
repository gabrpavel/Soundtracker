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
@Table(name = "actors")
@Schema(description = "Актер")
public class Actor {

    @Id
    @Schema(description = "ID актера", example = "20087")
    private Long id;

    @Column(name = "ru_name")
    @Schema(description = "Имя актера на русском языке", example = "Робин Уильямс")
    private String ruName;

    @Column(name = "en_name")
    @Schema(description = "Имя актера на английском языке", example = "Robin Williams")
    private String enName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    private Set<Movie> movies = new HashSet<>();

    public Actor(Long id, String ruName, String enName) {
        this.id = id;
        this.ruName = ruName;
        this.enName = enName;
    }
}
