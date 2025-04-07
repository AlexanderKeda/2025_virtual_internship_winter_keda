package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumRequestValidatorImp implements TravelCalculatePremiumRequestValidator {

    private final List<TravelRequestValidation> travelValidations;

    @Override
    public List<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        List<ValidationError> singleErrors = collectSingleErrors(request);
        List<ValidationError> listErrors = collectListErrors(request);

        return concatenateLists(singleErrors, listErrors);
    }

    private List<ValidationError> collectSingleErrors(TravelCalculatePremiumRequestV1 request) {
        return travelValidations.stream()
                .map(validation -> validation.validate(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationError> collectListErrors(TravelCalculatePremiumRequestV1 request) {
        return travelValidations.stream()
                .map(validation -> validation.validateList(request))
                .flatMap(List::stream)
                .toList();
    }

    private List<ValidationError> concatenateLists(List<ValidationError> singleErrors, List<ValidationError> listErrors) {
        return Stream.concat(singleErrors.stream(), listErrors.stream())
                .toList();
    }

}
