package com.liapkalo.profitsoft.filmwebapp.service;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;

import java.util.List;

public interface DirectorService {

    Director createDirector(DirectorDto directorDto);

    List<Director> getDirectors();

    Director updateDirector(Long id, DirectorDto directorDto);

    String deleteDirector(Long id);

}
