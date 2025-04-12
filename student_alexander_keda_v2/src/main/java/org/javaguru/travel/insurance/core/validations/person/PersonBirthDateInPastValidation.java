package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class PersonBirthDateInPastValidation implements TravelPersonValidation {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(PersonDTO person) {
        return (person.getPersonBirthDate() != null
                && person.getPersonBirthDate().isAfter(LocalDate.now()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_13"))
                : Optional.empty();
    }
}
