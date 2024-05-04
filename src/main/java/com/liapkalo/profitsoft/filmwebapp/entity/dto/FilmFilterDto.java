package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FilmFilterDto {

    String name;

    String genre;

    DirectorDto director;

    public FilmFilterDto(String name, String genre, DirectorDto director) {
        this.name = name;
        this.genre = genre;
        this.director = director;
    }

    @Override
    public String toString() {
        return  name+","+genre+","+director.name;
    }
}
