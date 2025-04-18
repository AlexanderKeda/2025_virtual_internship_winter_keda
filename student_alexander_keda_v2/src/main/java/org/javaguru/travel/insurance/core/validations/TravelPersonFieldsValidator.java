package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.person.TravelPersonFieldsValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPersonFieldsValidator {

    private final List<TravelPersonFieldsValidation> personFieldsValidations;

    List<ValidationErrorDTO> validate(List<PersonDTO> persons) {
        return persons != null
                ? validatePersons(persons)
                : List.of();
    }

    private List<ValidationErrorDTO> validatePersons(List<PersonDTO> persons) {
        return persons.stream()
                .map(this::collectPersonErrors)
                .flatMap(List::stream)
                .toList();
    }

    private List<ValidationErrorDTO> collectPersonErrors(PersonDTO person) {
        List<ValidationErrorDTO> singleErrors = collectSingleErrors(person);
        List<ValidationErrorDTO> listErrors = collectListErrors(person);

        return concatenateLists(singleErrors, listErrors);
    }

    private List<ValidationErrorDTO> collectSingleErrors(PersonDTO person) {
        return personFieldsValidations.stream()
                .map(validation -> validation.validate(person))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationErrorDTO> collectListErrors(PersonDTO person) {
        return personFieldsValidations.stream()
                .map(validation -> validation.validateList(person))
                .flatMap(List::stream)
                .toList();
    }

    private List<ValidationErrorDTO> concatenateLists(List<ValidationErrorDTO> list1, List<ValidationErrorDTO> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .toList();
    }

}
