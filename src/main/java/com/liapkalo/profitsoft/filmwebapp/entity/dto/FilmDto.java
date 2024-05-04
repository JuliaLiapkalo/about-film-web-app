package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import com.liapkalo.profitsoft.filmwebapp.annotation.ValidYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {

    @NotNull
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    String name;

    @NotNull
    @NotBlank(message = "Genre cannot be blank")
    @Length(min = 1, max = 255, message = "Genre must be between 1 and 255 characters")
    String genre;

    @NotNull(message = "Release year cannot be null")
    @ValidYear(message = "Invalid release year")
    Integer releaseYear;

    @NotNull(message = "Main actors list cannot be null")
    @Size(min = 1, message = "Main actors list must contain at least one actor")
    List<ActorDto> mainActors;

    @NotNull(message = "Director cannot be null")
    DirectorDto director;
}
