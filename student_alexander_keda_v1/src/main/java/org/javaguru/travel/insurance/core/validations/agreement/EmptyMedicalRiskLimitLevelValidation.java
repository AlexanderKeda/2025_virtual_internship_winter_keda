package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.TravelRequestValidation;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return isMedicalRiskLimitLevelEnabled()
                && hasRequiredRisks(request)
                ? validateEmptyMedicalRiskLimitLevel(request)
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean hasRequiredRisks(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private Optional<ValidationError> validateEmptyMedicalRiskLimitLevel
            (TravelCalculatePremiumRequestV1 request) {
        return  request.getMedicalRiskLimitLevel() == null
                || request.getMedicalRiskLimitLevel().isBlank()
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }
}
