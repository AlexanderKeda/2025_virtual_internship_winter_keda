package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyMedicalRiskLimitLevelValidation implements TravelRequestValidation {

    private final boolean medicalRiskLimitLevelEnabled;

    private final ValidationErrorFactory validationErrorFactory;

    EmptyMedicalRiskLimitLevelValidation(
            @Value("${medical.risk.limit.level.enabled:false}") boolean medicalRiskLimitLevelEnabled,
            ValidationErrorFactory validationErrorFactory
    ) {
        this.medicalRiskLimitLevelEnabled = medicalRiskLimitLevelEnabled;
        this.validationErrorFactory = validationErrorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return isMedicalRiskLimitLevelEnabled()
                && hasRequiredRisks(request)
                ? validateEmptyMedicalRiskLimitLevel(request)
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean hasRequiredRisks(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private Optional<ValidationError> validateEmptyMedicalRiskLimitLevel
            (TravelCalculatePremiumRequest request) {
        return  request.getMedicalRiskLimitLevel() == null
                || request.getMedicalRiskLimitLevel().isBlank()
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }
}
