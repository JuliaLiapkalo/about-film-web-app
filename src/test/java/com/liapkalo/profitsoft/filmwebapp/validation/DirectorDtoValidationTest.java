package com.liapkalo.profitsoft.filmwebapp.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.DirectorUtils.buildDirectorDto;
import static utils.DirectorUtils.buildDirectorDtoInvalid;

public class DirectorDtoValidationTest {

    private final Validator validator;

    public DirectorDtoValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDirectorDto() {
        assertEquals(0, validator.validate(buildDirectorDto()).size());
    }

    @Test
    void testInvalidDirectorDto() {
        assertEquals(3, validator.validate(buildDirectorDtoInvalid()).size());
    }
}
