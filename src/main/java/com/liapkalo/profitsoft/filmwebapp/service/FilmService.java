package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface FilmService {

    Film createFilm(FilmDto filmDto);

    Film getFilm(Long id);

    Film updateFilm(Long id, FilmDto filmDto);

    String deleteFilm(Long id);

    List<Film> getFilteredFilms(FilmFilterDto filmFilterDto);

    Map<String, Object> getFilteredFilmsByPage(FilmFilterDto filmFilterDto, Pageable pageable);

}
