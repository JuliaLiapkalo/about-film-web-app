package com.liapkalo.profitsoft.filmwebapp.entity.validator;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class DirectorValidator {

    public static boolean invalidDirector(DirectorDto director) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DirectorDto>> directorViolations = validator.validate(director);
        if (!directorViolations.isEmpty()) {
            log.error("DirectorDto validation failed: {}", directorViolations);
            return true;
        }
        return false;
    }
}
