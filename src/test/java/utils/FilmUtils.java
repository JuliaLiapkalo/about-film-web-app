package utils;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import lombok.experimental.UtilityClass;

import java.util.Collections;

@UtilityClass
public class FilmUtils {

    public static FilmDto buildFilmDto() {
       return FilmDto.builder()
                .name("Film Name")
                .genre("Action")
                .releaseYear(2022)
                .director(new Director())
                .mainActors(Collections.singletonList(new Actor()))
               .build();
    }

    public static FilmDto buildEmptyFilmDto() {
        return FilmDto.builder()
                .name("")
                .genre("")
                .releaseYear(3022)
                .director(null)
                .mainActors(Collections.emptyList())
                .build();
    }

    public static FilmDto buildDtoForUpdate() {
        return FilmDto.builder()
                .name("Updated Film Name")
                .genre("Updated Genre")
                .build();
    }

    public static Film buildFilm() {
        return Film.builder()
                .id(1L)
                .name("FilmName")
                .genre("FilmGenre")
                .releaseYear(1999)
                .director(new Director())
                .mainActors(Collections.singletonList(new Actor()))
                .build();
    }
}
