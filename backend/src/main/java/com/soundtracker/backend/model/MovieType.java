package com.soundtracker.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_types")
public class MovieType {

    @Id
    @JsonIgnore
    private Long id;

    @Column(name = "type")
    private String name;
}
