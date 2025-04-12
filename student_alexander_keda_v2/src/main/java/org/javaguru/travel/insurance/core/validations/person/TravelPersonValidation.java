package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.Optional;

public interface TravelPersonValidation {

    default Optional<ValidationErrorDTO> validate(PersonDTO person) {
        return Optional.empty();
    }

}