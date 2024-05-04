package com.liapkalo.profitsoft.filmwebapp.entity.mapper;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DirectorMapper.class, ActorMapper.class})
public interface FilmMapper {

    Film toFilm(FilmDto filmDto);
    FilmFilterDto toFilmFilterDto(Film film);

}
