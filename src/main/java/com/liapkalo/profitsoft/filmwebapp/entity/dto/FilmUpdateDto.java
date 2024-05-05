package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import com.liapkalo.profitsoft.filmwebapp.annotation.ValidYear;
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
public class FilmUpdateDto {

    @Length(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    String name;

    @Length(min = 1, max = 255, message = "Genre must be between 1 and 255 characters")
    String genre;

    @ValidYear(message = "Invalid release year")
    Integer releaseYear;

    @Size(min = 1, message = "Main actors list must contain at least one actor")
    List<ActorDto> mainActors;

    DirectorUpdateDto director;
}
