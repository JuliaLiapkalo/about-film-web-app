package com.liapkalo.profitsoft.filmwebapp.controller;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import com.liapkalo.profitsoft.filmwebapp.service.CsvService;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.liapkalo.profitsoft.filmwebapp.utils.ListUtils.createResponseList;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/films")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilmController {

    FilmService filmService;
    CsvService csvService;

    @PostMapping
    public ResponseEntity<?> addFilm(@Valid @RequestBody FilmDto filmDto) {
        return ResponseEntity.ok().body(filmService.createFilm(filmDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilm(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(filmService.getFilm(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable("id") Long id, @RequestBody FilmDto film) {
        return ResponseEntity.ok().body(filmService.updateFilm(id, film));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(filmService.deleteFilm(id));
    }

    @PostMapping("/_list")
    public ResponseEntity<?> getFilmsByPage(@RequestBody(required = false) FilmFilterDto filmFilterDto,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok().body(createResponseList(filmService.getFilmsFromList(filmFilterDto,
                                PageRequest.of(page, size, Sort.by("id")))));
    }

    @PostMapping(value = "/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getFilmsReport(HttpServletResponse response, @RequestBody FilmFilterDto filmFilterDto) {
        csvService.generateFilmCsvReport(response, filmFilterDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> processFilmsJson(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(filmService.getFilmsFromJson(file));
    }
}
