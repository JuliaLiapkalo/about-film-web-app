package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;

public interface CsvService {

     void generateFilmCsvReport(HttpServletResponse response, FilmFilterDto filmFilterDto);

     void createFilmCsv(FilmFilterDto filmFilterDto, OutputStream outputStream);

}
