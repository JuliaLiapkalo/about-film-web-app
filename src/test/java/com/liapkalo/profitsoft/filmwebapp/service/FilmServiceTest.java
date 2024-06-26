package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmUpdateDto;
import com.liapkalo.profitsoft.filmwebapp.entity.mapper.FilmMapper;
import com.liapkalo.profitsoft.filmwebapp.repository.FilmRepository;
import com.liapkalo.profitsoft.filmwebapp.service.impl.ActorServiceImpl;
import com.liapkalo.profitsoft.filmwebapp.service.impl.DirectorServiceImpl;
import com.liapkalo.profitsoft.filmwebapp.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static utils.FilmUtils.*;

public class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmMapper filmMapper;

    @Mock
    private DirectorServiceImpl directorServiceImpl;

    @Mock
    private ActorServiceImpl actorServiceImpl;

    @InjectMocks
    private FilmServiceImpl filmService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateFilm() {
        FilmDto filmDto = buildFilmDto();

        Film expectedFilm = new Film();

        when(directorServiceImpl.getOrCreateDirector(any())).thenReturn(new Director());
        when(actorServiceImpl.getOrCreateActor(any())).thenReturn(new Actor());
        when(filmRepository.save(any())).thenReturn(expectedFilm);
        when(filmMapper.toFilm(any())).thenReturn(expectedFilm);

        Film createdFilm = filmService.createFilm(filmDto);

        assertEquals(expectedFilm, createdFilm);
        verify(filmRepository, times(1)).save(any());
    }

    @Test
    void testUpdateFilm() {
        Long filmId = 1L;
        FilmUpdateDto filmDto = buildDtoForUpdate();

        Film existingFilm = buildFilm();

        when(filmRepository.findById(filmId)).thenReturn(Optional.of(existingFilm));
        when(filmRepository.save(any(Film.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Film updatedFilm = filmService.updateFilm(filmId, filmDto);

        assertEquals(filmDto.getName(), updatedFilm.getName());
        verify(filmRepository).save(existingFilm);
    }

    @Test
    void testUpdateFilmNotFound() {
        Long filmId = 1L;
        FilmUpdateDto filmDto = new FilmUpdateDto();

        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> filmService.updateFilm(filmId, filmDto));
        assertEquals("Film with id " + filmId + " not found", exception.getMessage());
        verify(filmRepository, never()).save(any());
    }

}

