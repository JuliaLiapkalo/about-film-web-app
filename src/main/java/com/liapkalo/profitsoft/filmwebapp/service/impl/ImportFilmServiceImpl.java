package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import com.liapkalo.profitsoft.filmwebapp.service.ImportFilmService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.liapkalo.profitsoft.filmwebapp.entity.validator.FilmValidator.invalidFilm;
import static com.liapkalo.profitsoft.filmwebapp.utils.JsonParseUtils.validateJson;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImportFilmServiceImpl implements ImportFilmService {

    ObjectMapper objectMapper;
    FilmService filmService;

    /**
     * Parses a JSON file containing film data and saves the films to the database.
     * This method reads the content of the provided JSON file using a JsonParser and saves each film to the database.
     * The JSON file is expected to contain an array of film objects.
     *
     * @param file The JSON file containing film data.
     * IOException If an I/O error occurs while parsing the JSON file.
     */
    @Override
    @Transactional
    public Map<String, Integer> importFilmsFromJson(MultipartFile file) {
        log.info("Parsing file: {}", file.getOriginalFilename());
        Map<String, Integer> importStatistic = new HashMap<>();
        try (InputStream inputStream = file.getInputStream();
             JsonParser parser = objectMapper.getFactory().createParser(inputStream)) {
             validateJson(parser);

             importStatistic = processFilmsFromJson(parser);

             log.info("File parsed successfully: {}", file.getOriginalFilename());
        } catch (IOException e) {
            log.error("Error parsing file: {}", file.getOriginalFilename(), e);
        }
        return importStatistic;
    }

    /**
     * Adds films from a JSON parser.
     *
     * @param parser The JSON parser.
     * @return A map containing import statistics.
     * @throws IOException If an I/O error occurs.
     */
    @Transactional
    @Override
    public Map<String, Integer> processFilmsFromJson(JsonParser parser) throws IOException {
        Map<String, Integer> importStatistic = new HashMap<>();
        importStatistic.put("Successes", 0);
        importStatistic.put("Fails", 0);

        while (parser.nextToken() != JsonToken.END_ARRAY) {
            FilmDto filmDto = parser.readValueAs(FilmDto.class);
            if (invalidFilm(filmDto)) {
                importStatistic.compute("Fails", (k, fails) -> fails + 1);
            } else {
                filmService.createFilm(filmDto);
                importStatistic.compute("Successes", (k, successes) -> successes + 1);
            }
        }
        return importStatistic;
    }

}
