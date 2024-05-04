package com.liapkalo.profitsoft.filmwebapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liapkalo.profitsoft.filmwebapp.FilmWebApplication;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static utils.DirectorUtils.*;

@SpringBootTest (
webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = FilmWebApplication.class)
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
        Director director = buildDirector();

        when(directorService.getDirectors()).thenReturn(List.of(director));

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/directors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        List<Director> expectedDirectors = List.of(buildDirector());

        ObjectMapper objectMapper = new ObjectMapper();
        List<Director> actualDirectors = objectMapper.readValue(responseBody, new TypeReference<>() {});
        ArrayList<Director> expectedList = new ArrayList<>(expectedDirectors);

        assertThat(actualDirectors.get(0).getName()).isEqualTo(expectedList.get(0).getName());
        assertThat(actualDirectors.get(0).getAge()).isEqualTo(expectedList.get(0).getAge());
    }

    @Test
    public void getAllDirectorsTest_ZeroDirectors() throws Exception {
        when(directorService.getDirectors()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/directors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("[]");
    }


    @Test
    public void addDirectorTest() throws Exception {
        DirectorDto directorDto = buildDirectorDto();

        mockMvc.perform(post("/api/v1/directors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void addDirectorTest_invalidDirector() throws Exception {
        DirectorDto directorDto = buildDirectorDtoInvalid();

        mockMvc.perform(post("/api/v1/directors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))
                .andExpect(status().isBadRequest());
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
