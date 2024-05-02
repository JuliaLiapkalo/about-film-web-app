package com.liapkalo.profitsoft.filmwebapp.builder;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilmBuilder {

    ActorBuilder actorBuilder;
    DirectorBuilder directorBuilder;

    public Film buildFilm(FilmDto request) {
        return Film.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .releaseYear(request.getReleaseYear())
                .mainActors(actorBuilder.buildActors(request.getMainActors()))
                .director(directorBuilder.buildDirector(request.getDirector()))
                .build();
    }

}
