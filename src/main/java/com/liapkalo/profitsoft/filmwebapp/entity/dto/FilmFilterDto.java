package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FilmFilterDto {

    String name;

    String genre;

    Director director;

    public FilmFilterDto(String name, String genre, Director director) {
        this.name = name;
        this.genre = genre;
        this.director = director;
    }

}
