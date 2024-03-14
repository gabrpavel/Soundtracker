package com.soundtracker.backend.dto.response.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Ответ на поиск movie по id")
public class MovieDto {

    @Schema(description = "Id фильма с кинопоиска", example = "342")
    Long id;

    @Schema(description = "Название на русском языке", example = "Криминальное чтиво")
    String name;

    String alternativeName;
    String enName;
    String type;
    int typeNumber;
    int year;
    String description;
    int movieLength;

    public MovieDto(Long id, String name, String alternativeName, String enName, String type,
                    int typeNumber, int year, String description, int movieLength) {
        this.id = id;
        this.name = name;
        this.alternativeName = alternativeName;
        this.enName = enName;
        this.type = type;
        this.typeNumber = typeNumber;
        this.year = year;
        this.description = description;
        this.movieLength = movieLength;
    }
}
