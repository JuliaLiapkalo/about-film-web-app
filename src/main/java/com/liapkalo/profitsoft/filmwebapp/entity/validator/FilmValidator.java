package com.liapkalo.profitsoft.filmwebapp.entity.validator;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static com.liapkalo.profitsoft.filmwebapp.entity.validator.ActorValidator.invalidActors;
import static com.liapkalo.profitsoft.filmwebapp.entity.validator.DirectorValidator.invalidDirector;

@Slf4j
public class FilmValidator {

    public static boolean invalidFilm(FilmDto filmDto) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);

        if (!violations.isEmpty() || invalidActors(filmDto.getMainActors()) ||
            invalidDirector(filmDto.getDirector())) {
            log.error("FilmDto validation failed: {}", violations);
            return true;
        }
        return false;
    }


}
