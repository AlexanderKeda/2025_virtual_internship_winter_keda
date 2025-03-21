package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class MedicalRiskLimitLevelExistenceValidation implements TravelRequestValidation {

    private final boolean medicalRiskLimitLevelEnabled;

    private final ClassifierValueRepository classifierValueRepository;

    private final ValidationErrorFactory validationErrorFactory;

    MedicalRiskLimitLevelExistenceValidation(
            @Value("${medical.risk.limit.level.enabled:false}") boolean medicalRiskLimitLevelEnabled,
            ClassifierValueRepository classifierValueRepository,
            ValidationErrorFactory validationErrorFactory
    ) {
        this.medicalRiskLimitLevelEnabled = medicalRiskLimitLevelEnabled;
        this.classifierValueRepository = classifierValueRepository;
        this.validationErrorFactory = validationErrorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return medicalRiskLimitLevelEnabled
                && hasRequiredRisks(request)
                && request.getMedicalRiskLimitLevel() != null
                && !request.getMedicalRiskLimitLevel().isBlank()
                ? validateMedicalRiskLimitLevelExistence(request)
                : Optional.empty();
    }

    private boolean hasRequiredRisks(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private Optional<ValidationError> validateMedicalRiskLimitLevelExistence
            (TravelCalculatePremiumRequest request) {
        return doesLimitLevelExist(request.getMedicalRiskLimitLevel())
                ? Optional.empty()
                : Optional.of(buildLimitLevelNotFoundError(request.getMedicalRiskLimitLevel()));
    }

    private boolean doesLimitLevelExist(String limitLevel) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel)
                .isPresent();
    }

    private ValidationError buildLimitLevelNotFoundError(String limitLevel) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_16",
                        List.of(new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel))
                );
    }
}
