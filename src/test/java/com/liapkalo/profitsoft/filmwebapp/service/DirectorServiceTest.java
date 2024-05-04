package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.mapper.DirectorMapper;
import com.liapkalo.profitsoft.filmwebapp.repository.DirectorRepository;
import com.liapkalo.profitsoft.filmwebapp.service.impl.DirectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static utils.DirectorUtils.*;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private DirectorServiceImpl directorService;

    @Mock
    private DirectorMapper directorMapper;

    @BeforeEach
    void setUp() {
        directorService = new DirectorServiceImpl(directorRepository, directorMapper);
    }

    @Test
    void testGetOrCreateDirector() {
        DirectorDto directorDto = new DirectorDto("Test Director", 40);

        when(directorRepository.findByNameAndAge("Test Director", 40)).thenReturn(Optional.empty());
        when(directorRepository.save(any(Director.class))).thenReturn(buildDirector());
        when(directorMapper.toDirector(any())).thenReturn(buildDirector());

        Director createdDirector = directorService.getOrCreateDirector(directorDto);

        assertNotNull(createdDirector);
        assertEquals("Director", createdDirector.getName());
        assertEquals(40, createdDirector.getAge());
        verify(directorRepository, times(1)).findByNameAndAge("Test Director", 40);
        verify(directorRepository, times(1)).save(any(Director.class));
    }

    @Test
    void testUpdateDirector() {
        DirectorDto directorDto = buildDirectorDto();

        Director director = buildDirector();
        when(directorRepository.findById(director.getId())).thenReturn(Optional.of(director));

        directorService.updateDirector(director.getId(), directorDto);

        assertNotNull(director);
        assertEquals("Updated director", director.getName());
        assertEquals(45, director.getAge());
        verify(directorRepository, times(1)).findById(director.getId());
        verify(directorRepository, times(1)).save(director);
    }

    @Test
    void testUpdateDirector_NotValidName() {
        DirectorDto directorDto = buildDirectorDtoInvalidName();

        Director director = buildDirector();
        when(directorRepository.findById(director.getId())).thenReturn(Optional.of(director));

        directorService.updateDirector(director.getId(), directorDto);

        assertNotNull(director);
        assertEquals("Director", director.getName());
        assertEquals(45, director.getAge());
        verify(directorRepository, times(1)).findById(director.getId());
        verify(directorRepository, times(1)).save(director);
    }

}
