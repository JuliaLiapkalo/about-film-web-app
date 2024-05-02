package com.liapkalo.profitsoft.filmwebapp.builder;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import com.liapkalo.profitsoft.filmwebapp.service.ActorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActorBuilder {

    ActorService actorService;

    public List<Actor> buildActors(List<Actor> actor) {
        return actor.stream()
                .map(a -> actorService.createActor(new ActorDto(a.getName())))
                .toList();
    }

}
