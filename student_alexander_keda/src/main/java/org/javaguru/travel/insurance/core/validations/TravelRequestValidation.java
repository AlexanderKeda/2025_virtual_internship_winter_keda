package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;

import java.util.List;
import java.util.Optional;

interface TravelRequestValidation {

    default Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return Optional.empty();
    }

    default List<ValidationError> validateList(TravelCalculatePremiumRequest request) {
        return List.of();
    }

}
