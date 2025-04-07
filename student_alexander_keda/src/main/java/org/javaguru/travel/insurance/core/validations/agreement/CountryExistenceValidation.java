package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.TravelRequestValidation;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CountryExistenceValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;
    private final ClassifierValueRepository classifierValueRepository;
    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return request.getCountry() != null
                && !request.getCountry().isBlank()
                ? validateCountryExistence(request)
                : Optional.empty();
    }

    private Optional<ValidationError> validateCountryExistence(TravelCalculatePremiumRequest request) {
        return doesCountryExist(request.getCountry())
                && doesDefaultDayRateExist(request.getCountry())
                ? Optional.empty()
                : Optional.of(buildCountryNotFoundError(request.getCountry()));
    }

    private boolean doesCountryExist(String countryIc) {
        return classifierValueRepository
                .existsByClassifierTitleAndIc("COUNTRY", countryIc);
    }

    private boolean doesDefaultDayRateExist(String countryIc) {
        return countryDefaultDayRateRepository
                .existsByCountryIc(countryIc);
    }

    private ValidationError buildCountryNotFoundError(String countryIc) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_11",
                        List.of(new Placeholder("NOT_EXISTING_COUNTRY", countryIc))
                );
    }
}
