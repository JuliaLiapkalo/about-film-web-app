package com.liapkalo.profitsoft.filmwebapp.builder;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.service.DirectorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DirectorBuilder {

    DirectorService directorService;

    public Director buildDirector(Director director) {
        return directorService.createDirector(
                new DirectorDto(director.getName(), director.getAge()));
    }
}
