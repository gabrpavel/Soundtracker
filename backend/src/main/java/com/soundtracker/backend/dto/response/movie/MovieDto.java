package com.soundtracker.backend.dto.response.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Ответ на поиск movie по id")
public class MovieDto {

    @Schema(description = "ID кино", example = "4996")
    Long id;

    @Schema(description = "Название на русском языке", example = "Общество мертвых поэтов")
    String ruTitle;

    @Schema(description = "Альтернативное название", example = "Dead Poets Society")
    String enTitle;

    @Schema(description = "Тип кино (movie | tv-series | cartoon | anime | animated-series | tv-show)", example = "movie")
    String type;

    @Schema(description = "Год производства", example = "1989")
    int releaseYear;

    @Schema(description = "Продолжительность", example = "128")
    int length;

    @Schema(description = "Постер", example = "https://image.openmoviedb.com/kinopoisk-images/1599028/66058481-9b77-4b51-9d4b-65db5cd796d2/orig")
    private String poster;

    @Schema(description = "Жанр", example = "драма")
    private Set<String> genres = new HashSet<>();
}
