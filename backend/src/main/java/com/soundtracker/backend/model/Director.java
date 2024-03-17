package com.soundtracker.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Director {

    @Id
    private Long id;

    @Column(name = "ru_name")
    private String ruName;

    @Column(name = "en_name")
    private String enName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "directors",
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            })
    private Set<Movie> movies = new HashSet<>();

    public Director(Long id, String ruName, String enName) {
        this.id = id;
        this.ruName = ruName;
        this.enName = enName;
    }
}
