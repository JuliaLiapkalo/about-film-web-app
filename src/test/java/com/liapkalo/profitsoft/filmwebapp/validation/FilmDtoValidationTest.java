package com.liapkalo.profitsoft.filmwebapp.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.FilmUtils.buildEmptyFilmDto;
import static utils.FilmUtils.buildFilmDto;

public class FilmDtoValidationTest {

    private final Validator validator;

    public FilmDtoValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidFilmDto() {
        assertEquals(0, validator.validate(buildFilmDto()).size());
    }

    @Test
    void testInvalidFilmDto() {
        assertEquals(7, validator.validate(buildEmptyFilmDto()).size());
    }
}
