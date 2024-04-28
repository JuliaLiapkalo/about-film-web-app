package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.OutputStream;

public interface CsvService {

    int createCsvLine(OutputStream outputStream, int pageNumber,
                                     Page<FilmNameAndGenreDto> filmsPage) throws IOException;
}
