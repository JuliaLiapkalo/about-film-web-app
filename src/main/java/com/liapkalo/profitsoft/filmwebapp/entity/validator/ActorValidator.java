package com.liapkalo.profitsoft.filmwebapp.entity.validator;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class ActorValidator {

    public static boolean invalidActors(List<ActorDto> actors) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (ActorDto actor : actors) {
            Set<ConstraintViolation<ActorDto>> actorViolations = validator.validate(actor);
            if (!actorViolations.isEmpty()) {
                log.error("ActorDto validation failed: {}", actorViolations);
                return true;
            }
        }

        return false;
    }
}
