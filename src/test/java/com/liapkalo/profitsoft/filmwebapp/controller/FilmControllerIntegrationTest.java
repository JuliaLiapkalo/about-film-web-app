package com.liapkalo.profitsoft.filmwebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.FilmUtils.buildFilm;
import static utils.FilmUtils.buildFilmDto;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addFilmSuccessTest() throws Exception {
        FilmDto filmDto = buildFilmDto();
        Film createdFilm = buildFilm();

        when(filmService.createFilm(any(FilmDto.class))).thenReturn(createdFilm);

        mockMvc.perform(post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdFilm.getId().intValue())))
                .andExpect(jsonPath("$.name", is(createdFilm.getName())))
                .andExpect(jsonPath("$.genre", is(createdFilm.getGenre())))
                .andExpect(jsonPath("$.releaseYear", is(createdFilm.getReleaseYear())))
                .andExpect(jsonPath("$.mainActors", is(equalTo(Collections.singletonList(Collections.emptyMap())))))
                .andExpect(jsonPath("$.director", is(createdFilm.getDirector())));
    }

    @Test
    public void addFilmValidationFailureTest() throws Exception {
        FilmDto filmDto = new FilmDto();

        mockMvc.perform(post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmDto)))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFilmServiceFailureTest() throws Exception {
        FilmDto filmDto = buildFilmDto();
        when(filmService.createFilm(any(FilmDto.class))).thenThrow(new RuntimeException("Film creation failed"));

        mockMvc.perform(post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmDto)))

                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Film creation failed")));
    }

}
