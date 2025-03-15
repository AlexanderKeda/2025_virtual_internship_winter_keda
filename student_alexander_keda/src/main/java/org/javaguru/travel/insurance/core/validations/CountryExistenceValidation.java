package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
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
        return hasRequiredRisks(request)
                && request.getCountry() != null
                && !request.getCountry().isBlank()
                ? validateCountryExistence(request)
                : Optional.empty();
    }

    private boolean hasRequiredRisks(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private Optional<ValidationError> validateCountryExistence(TravelCalculatePremiumRequest request) {
        return doesCountryExist(request.getCountry())
                && doesDefaultDayRateExist(request.getCountry())
                ? Optional.empty()
                : Optional.of(buildCountryNotFoundError(request.getCountry()));
    }

    private boolean doesCountryExist(String countryIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", countryIc)
                .isPresent();
    }

    private boolean doesDefaultDayRateExist(String countryIc) {
        return countryDefaultDayRateRepository
                .findByCountryIc(countryIc)
                .isPresent();
    }

    private ValidationError buildCountryNotFoundError(String countryIc) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_11",
                        List.of(new Placeholder("NOT_EXISTING_COUNTRY", countryIc))
                );
    }
}
