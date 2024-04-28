package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import com.liapkalo.profitsoft.filmwebapp.repository.ActorRepository;
import com.liapkalo.profitsoft.filmwebapp.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateActor_NewActor() {
        ActorDto actorDto = new ActorDto("John Doe");
        Actor newActor = new Actor("John Doe");

        when(actorRepository.findByName(actorDto.getName())).thenReturn(Optional.empty());
        when(actorRepository.save(any(Actor.class))).thenReturn(newActor);

        Actor createdActor = actorService.createActor(actorDto);

        assertNotNull(createdActor);
        assertEquals(newActor.getName(), createdActor.getName());
        verify(actorRepository, times(1)).findByName(actorDto.getName());
        verify(actorRepository, times(1)).save(any(Actor.class));
    }

    @Test
    void testCreateActor_ExistingActor() {
        ActorDto actorDto = new ActorDto("John Doe");
        Actor existingActor = new Actor("John Doe");

        when(actorRepository.findByName(actorDto.getName())).thenReturn(Optional.of(existingActor));

        Actor createdActor = actorService.createActor(actorDto);

        assertNotNull(createdActor);
        assertEquals(existingActor.getName(), createdActor.getName());
        verify(actorRepository, times(1)).findByName(actorDto.getName());
        verify(actorRepository, never()).save(any(Actor.class));
    }
}
