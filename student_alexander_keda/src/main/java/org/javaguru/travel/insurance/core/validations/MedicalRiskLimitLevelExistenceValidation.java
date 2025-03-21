package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
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
    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;
    private final ValidationErrorFactory validationErrorFactory;

    MedicalRiskLimitLevelExistenceValidation(
            @Value("${medical.risk.limit.level.enabled:false}") boolean medicalRiskLimitLevelEnabled,
            ClassifierValueRepository classifierValueRepository,
            MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository,
            ValidationErrorFactory validationErrorFactory
    ) {
        this.medicalRiskLimitLevelEnabled = medicalRiskLimitLevelEnabled;
        this.classifierValueRepository = classifierValueRepository;
        this.medicalRiskLimitLevelRepository = medicalRiskLimitLevelRepository;
        this.validationErrorFactory = validationErrorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return isMedicalRiskLimitLevelEnabled()
                && hasRequiredRisks(request)
                && request.getMedicalRiskLimitLevel() != null
                && !request.getMedicalRiskLimitLevel().isBlank()
                ? validateMedicalRiskLimitLevelExistence(request)
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean hasRequiredRisks(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private Optional<ValidationError> validateMedicalRiskLimitLevelExistence
            (TravelCalculatePremiumRequest request) {
        return doesLimitLevelIcExists(request.getMedicalRiskLimitLevel())
                && doesLimitLevelCoefficientExists(request.getMedicalRiskLimitLevel())
                ? Optional.empty()
                : Optional.of(buildLimitLevelNotFoundError(request.getMedicalRiskLimitLevel()));
    }

    private boolean doesLimitLevelIcExists(String limitLevel) {
        return classifierValueRepository
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel);
    }

    private boolean doesLimitLevelCoefficientExists(String limitLevel) {
        return medicalRiskLimitLevelRepository
                .existsByMedicalRiskLimitIc(limitLevel);
    }

    private ValidationError buildLimitLevelNotFoundError(String limitLevel) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_16",
                        List.of(new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel))
                );
    }
}
