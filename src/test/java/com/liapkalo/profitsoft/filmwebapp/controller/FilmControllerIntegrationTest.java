package com.liapkalo.profitsoft.filmwebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static utils.FilmUtils.*;

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
                .andExpect(jsonPath("$.mainActors[0].id").value(createdFilm.getMainActors().get(0).getId()))
                .andExpect(jsonPath("$.mainActors[0].name").value(createdFilm.getMainActors().get(0).getName()))
                .andExpect(jsonPath("$.director.id").value(createdFilm.getDirector().getId()))
                .andExpect(jsonPath("$.director.name").value(createdFilm.getDirector().getName()))
                .andExpect(jsonPath("$.director.age").value(createdFilm.getDirector().getAge()));
    }

    @Test
    public void addFilmValidationFailureTest() throws Exception {
        FilmDto filmDto = buildEmptyFilmDto();

        mockMvc.perform(post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFilmByIdTest() throws Exception {
        Film film = buildFilm();

        when(filmService.getFilm(film.getId())).thenReturn(film);

        mockMvc.perform(get("/api/v1/films/{id}", film.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFilmTest() throws Exception {
        Long filmId = 1L;
        FilmDto updatedFilmDto = new FilmDto();
        updatedFilmDto.setName("Updated Film Name");
        updatedFilmDto.setGenre("Updated Genre");
        updatedFilmDto.setReleaseYear(2022);

        Film updatedFilm = Film.builder()
                .id(filmId)
                .name(updatedFilmDto.getName())
                .genre(updatedFilmDto.getGenre())
                .releaseYear(updatedFilmDto.getReleaseYear())
                .build();

        when(filmService.updateFilm(eq(filmId), any(FilmDto.class))).thenReturn(updatedFilm);

        mockMvc.perform(put("/api/v1/films/{id}", filmId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFilmDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(filmId))
                .andExpect(jsonPath("$.name").value(updatedFilmDto.getName()))
                .andExpect(jsonPath("$.genre").value(updatedFilmDto.getGenre()))
                .andExpect(jsonPath("$.releaseYear").value(updatedFilmDto.getReleaseYear()));
    }

    @Test
    public void deleteFilmTest() throws Exception {
        Long filmId = 1L;

        when(filmService.deleteFilm(filmId)).thenReturn("Film with id [" + filmId + "] deleted successfully!");

        mockMvc.perform(delete("/api/v1/films/{id}", filmId))
                .andExpect(status().isOk());
    }

    @Test
    public void getFilmsByPageTest() throws Exception {
        FilmFilterDto filmFilterDto = FilmFilterDto.builder().build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/films/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmFilterDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFilmsReportTest() throws Exception {
        FilmFilterDto filmFilterDto = new FilmFilterDto();
        filmFilterDto.setName("Film Name");
        filmFilterDto.setGenre("Action");

        mockMvc.perform(post("/api/v1/films/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmFilterDto)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE));
    }

    @Test
    public void processFilmsJsonTest() throws Exception {
        String jsonContent = "[{\"name\":\"Film1\"," +
                "\"genre\":\"Action\"," +
                "\"releaseYear\":2022," +
                "\"mainActors\":[{\"name\":\"Actor1\"}]," +
                "\"director\":{\"name\":\"Director1\",\"age\":40}}]";
        MockMultipartFile file = new MockMultipartFile("file", "films.json",
                MediaType.APPLICATION_JSON_VALUE, jsonContent.getBytes());

        mockMvc.perform(multipart("/api/v1/films/upload")
                        .file(file))
                .andExpect(status().isOk());
    }


}
