package com.liapkalo.profitsoft.filmwebapp.entity.mapper;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    Director toDirector(DirectorDto dto);
    DirectorDto toDirectorDto(Director director);

}
