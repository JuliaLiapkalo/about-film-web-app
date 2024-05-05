package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorUpdateDto;

import java.util.List;

public interface DirectorService {

    Director getOrCreateDirector(DirectorDto directorDto);

    List<Director> getDirectors();

    Director updateDirector(Long id, DirectorUpdateDto directorDto);

    String deleteDirector(Long id);

}
