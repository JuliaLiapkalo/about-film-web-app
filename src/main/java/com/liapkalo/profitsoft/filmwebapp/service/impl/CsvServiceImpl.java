package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto;
import com.liapkalo.profitsoft.filmwebapp.service.CsvService;
import com.liapkalo.profitsoft.filmwebapp.utils.PaginationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
@AllArgsConstructor
public class CsvServiceImpl implements CsvService {

    PaginationUtils paginationUtils;

    public int createCsvLine(OutputStream outputStream, int pageNumber, Page<FilmNameAndGenreDto> filmsPage) throws IOException {
        for (FilmNameAndGenreDto film : filmsPage.getContent()) {
            String csvLine = String.format("%s,%s\n", film.getName(), film.getGenre());
            outputStream.write(csvLine.getBytes());
        }
        pageNumber++;
        return pageNumber;
    }
}
