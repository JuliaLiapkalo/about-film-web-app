package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import com.liapkalo.profitsoft.filmwebapp.service.CsvService;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CsvServiceImpl implements CsvService {

    FilmService filmService;

    /**
     * Generates a CSV report of films based on the provided criteria and writes it to the HttpServletResponse.
     * This method generates a CSV report containing the names and genres of films that match the provided criteria.
     * The report is written to the HttpServletResponse object as an attachment with the filename "films-report.csv".
     *
     * @param response              The HttpServletResponse object to which the CSV report will be written.
     * @param filmFilterDto   The data transfer object containing criteria for filtering films by name, genre, and director ID.
     * IOException          If an I/O error occurs while writing the CSV report to the HttpServletResponse.
     */
    @Override
    public void generateFilmCsvReport(HttpServletResponse response, FilmFilterDto filmFilterDto) {
        log.info("Generating film report");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=films-report.csv");

        try (OutputStream outputStream = response.getOutputStream()) {
            createFilmCsv(filmFilterDto, outputStream);

            outputStream.flush();
            log.info("Film report generated successfully!");
        } catch (IOException e) {
            log.error("Fail generate film report: {}", e.getMessage());
        }
    }

    /**
     * Creates a CSV file containing information about films based on the provided criteria.
     * This method retrieves films from the database that match the provided name, genre, or director ID.
     * If the provided filmNameAndGenreDto is not null, it filters films based on the criteria provided in the object.
     * If the filmNameAndGenreDto is null, it retrieves all films.
     * The films' information is written to the provided outputStream in CSV format.
     * Pagination is applied to handle large datasets.
     *
     * @param filmFilterDto The data transfer object containing criteria for filtering films by name, genre, and director ID.
     * @param outputStream         The OutputStream to which the CSV data will be written.
     */
    @Override
    public void createFilmCsv(FilmFilterDto filmFilterDto, OutputStream outputStream) {
        writeCsvHeader(outputStream);
        writeFilmsToCsv(outputStream, filmService.getFilteredFilms(filmFilterDto));
    }

    private void writeFilmsToCsv(OutputStream outputStream, List<Film> films) {
        for (Film film : films) {
            String csvLine = String.format("%s,%s\n", film.getName(), film.getGenre());
            try {
                outputStream.write(csvLine.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error writing CSV line", e);
            }
        }
    }

    private void writeCsvHeader(OutputStream outputStream) {
        String header = "Name,Genre\n";
        try {
            outputStream.write(header.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV header", e);
        }
    }
}
