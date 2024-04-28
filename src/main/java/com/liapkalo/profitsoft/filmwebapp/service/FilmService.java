package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

public interface FilmService {
    Film createFilm(FilmDto filmDto);
    Film getFilm(Long id);
    Film updateFilm(Long id, FilmDto filmDto);
    String deleteFilm(Long id);
    Page<FilmNameAndGenreDto> getFilmsFromList(FilmNameAndGenreDto filmNameAndGenreDto, Pageable pageable);
    void generateFilmReport(HttpServletResponse response, FilmNameAndGenreDto filmNameAndGenreDto);
    String getFilmsFromJson(MultipartFile file);
    void createFilmCsv(FilmNameAndGenreDto filmNameAndGenreDto, OutputStream outputStream) throws IOException;


}
