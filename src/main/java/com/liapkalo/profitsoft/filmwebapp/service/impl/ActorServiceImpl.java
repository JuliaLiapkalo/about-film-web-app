package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.mapper.ActorMapper;
import com.liapkalo.profitsoft.filmwebapp.repository.ActorRepository;
import com.liapkalo.profitsoft.filmwebapp.service.ActorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActorServiceImpl implements ActorService {

    ActorRepository actorRepository;
    ActorMapper actorMapper;

    /**
     * Creates an actor based on the provided ActorDto object.
     * This method first checks if an actor with the same name already exists in the database.
     * If an actor with the same name exists, it returns that actor.
     * If not, it creates a new actor using the provided ActorDto and saves it to the database.
     *
     * @param actorDto The data transfer object containing information about the actor to be created.
     * @return The created actor if it already exists in the database, otherwise a new actor saved to the database.
     */
    @Override
    public Actor getOrCreateActor(ActorDto actorDto) {
        log.info("Creating actor: {}", actorDto);
        Optional<Actor> actor = actorRepository.findByName(actorDto.getName());
        return actor.orElseGet(() -> actorRepository.save(actorMapper.toActor(actorDto)));
    }

    /**
     * Retrieves or creates actors based on a list of actor DTOs.
     *
     * @param actorDto The list of actor DTOs.
     * @return A list of actors.
     */
    @Override
    public List<Actor> getOrCreateActors(List<ActorDto> actorDto) {
        return actorDto.stream().map(this::getOrCreateActor).toList();
    }


}
