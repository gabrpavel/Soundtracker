package com.soundtracker.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "movie_types")
public class MovieType {

    @Id
    private Long id;

    @Column(name = "type")
    private String type;
}
