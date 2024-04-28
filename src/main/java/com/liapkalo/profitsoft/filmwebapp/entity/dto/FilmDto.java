package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import com.liapkalo.profitsoft.filmwebapp.annotation.ValidYear;
import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {

    @NotNull
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    String name;

    @NotNull
    @NotBlank(message = "Genre cannot be blank")
    @Size(min = 1, max = 255, message = "Genre must be between 1 and 255 characters")
    String genre;

    @NotNull(message = "Release year cannot be null")
    @ValidYear(message = "Invalid release year")
    Integer releaseYear;

    @NotNull(message = "Main actors list cannot be null")
    @Size(min = 1, message = "Main actors list must contain at least one actor")
    List<Actor> mainActors;

    @NotNull(message = "Director cannot be null")
    Director director;
}
