package com.liapkalo.profitsoft.filmwebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static utils.DirectorUtils.buildDirector;
import static utils.DirectorUtils.buildDirectorDto;

@SpringBootTest
@AutoConfigureMockMvc
public class DirectorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllDirectorsTest() throws Exception {

        when(directorService.getDirectors()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/directors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addDirectorTest() throws Exception {
        DirectorDto directorDto = buildDirectorDto();
        Director director = buildDirector();
        when(directorService.createDirector(any(DirectorDto.class))).thenReturn(director);

        mockMvc.perform(post("/api/v1/directors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateDirectorTest() throws Exception {
        Long directorId = 1L;
        DirectorDto directorDto = buildDirectorDto();
        Director updatedDirector = buildDirector();

        when(directorService.updateDirector(eq(directorId), any(DirectorDto.class))).thenReturn(updatedDirector);

        mockMvc.perform(put("/api/v1/directors/{id}", directorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedDirector.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedDirector.getName())))
                .andExpect(jsonPath("$.age", is(updatedDirector.getAge())));
    }

    @Test
    public void deleteDirectorTest() throws Exception {
        Long directorId = 1L;
        String expectedMessage = "Director with id [1] deleted successfully!";

        when(directorService.deleteDirector(eq(directorId))).thenReturn(expectedMessage);

        mockMvc.perform(delete("/api/v1/directors/{id}", directorId))

                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }


}
