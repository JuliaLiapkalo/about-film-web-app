package com.liapkalo.profitsoft.filmwebapp.entity.mapper;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    Actor toActor(ActorDto actor);
    List<Actor> toActors(List<ActorDto> actors);

}
