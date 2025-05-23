package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.TravelRequestValidation;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MedicalRiskLimitLevelExistenceValidation implements TravelRequestValidation {

    private final ClassifierValueRepository classifierValueRepository;
    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return isMedicalRiskLimitLevelNotBlank(request)
                ? validateMedicalRiskLimitLevelExistence(request)
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelNotBlank(TravelCalculatePremiumRequestV1 request) {
        return request.getMedicalRiskLimitLevel() != null
                && !request.getMedicalRiskLimitLevel().isBlank();
    }

    private Optional<ValidationError> validateMedicalRiskLimitLevelExistence
            (TravelCalculatePremiumRequestV1 request) {
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
                .existsByMedicalRiskLimitLevelIc(limitLevel);
    }

    private ValidationError buildLimitLevelNotFoundError(String limitLevel) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_16",
                        List.of(new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel))
                );
    }
}
