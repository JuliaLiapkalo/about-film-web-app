package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;

import java.util.List;

public interface ActorService {

    Actor getOrCreateActor(ActorDto actorDto);

    List<Actor> getOrCreateActors(List<ActorDto> actorDto);

}
