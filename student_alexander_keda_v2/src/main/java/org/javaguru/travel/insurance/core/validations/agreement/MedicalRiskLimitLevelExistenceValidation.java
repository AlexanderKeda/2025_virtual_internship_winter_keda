package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MedicalRiskLimitLevelExistenceValidation implements TravelAgreementFieldsValidation {

    private final ClassifierValueRepository classifierValueRepository;
    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return isMedicalRiskLimitLevelNotBlank(agreement)
                ? validateMedicalRiskLimitLevelExistence(agreement)
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelNotBlank(AgreementDTO agreement) {
        return agreement.getMedicalRiskLimitLevel() != null
                && !agreement.getMedicalRiskLimitLevel().isBlank();
    }

    private Optional<ValidationErrorDTO> validateMedicalRiskLimitLevelExistence
            (AgreementDTO agreement) {
        return doesLimitLevelIcExists(agreement.getMedicalRiskLimitLevel())
                && doesLimitLevelCoefficientExists(agreement.getMedicalRiskLimitLevel())
                ? Optional.empty()
                : Optional.of(buildLimitLevelNotFoundError(agreement.getMedicalRiskLimitLevel()));
    }

    private boolean doesLimitLevelIcExists(String limitLevel) {
        return classifierValueRepository
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel);
    }

    private boolean doesLimitLevelCoefficientExists(String limitLevel) {
        return medicalRiskLimitLevelRepository
                .existsByMedicalRiskLimitLevelIc(limitLevel);
    }

    private ValidationErrorDTO buildLimitLevelNotFoundError(String limitLevel) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_16",
                        List.of(new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel))
                );
    }
}
