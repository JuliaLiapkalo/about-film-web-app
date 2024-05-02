package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;

public interface ActorService {

    Actor createActor(ActorDto actorDto);

}
