package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FilmNameAndGenreDto {

    String name;

    String genre;

    Long directorId;

    public FilmNameAndGenreDto(String name, String genre, Long directorId) {
        this.name = name;
        this.genre = genre;
        this.directorId = directorId;
    }
}
