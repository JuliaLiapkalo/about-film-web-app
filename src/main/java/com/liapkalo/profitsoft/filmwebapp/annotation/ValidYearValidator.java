package com.liapkalo.profitsoft.filmwebapp.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class ValidYearValidator implements ConstraintValidator<ValidYear, Integer> {

    @Override
    public boolean isValid(Integer releaseYear, ConstraintValidatorContext constraintValidatorContext) {
        if (releaseYear == null) { return true; }
        int currentYear = Year.now().getValue();
        return releaseYear < currentYear;
    }
}
