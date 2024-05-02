package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FilmService {

    Film createFilm(FilmDto filmDto);

    Film getFilm(Long id);

    Film updateFilm(Long id, FilmDto filmDto);

    String deleteFilm(Long id);

    List<Film> getFilteredFilms(FilmFilterDto filmFilterDto);

    Page<Film> getFilmsFromList(FilmFilterDto filmFilterDto, Pageable pageable);

    Map<String, Integer> getFilmsFromJson(MultipartFile file);

}
