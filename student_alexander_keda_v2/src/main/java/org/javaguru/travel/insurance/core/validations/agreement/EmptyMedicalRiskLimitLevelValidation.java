package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyMedicalRiskLimitLevelValidation implements TravelAgreementFieldsValidation {

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
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return isMedicalRiskLimitLevelEnabled()
                && hasRequiredRisks(agreement)
                ? validateEmptyMedicalRiskLimitLevel(agreement)
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean hasRequiredRisks(AgreementDTO agreement) {
        return agreement.getSelectedRisks() != null
                && agreement.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private Optional<ValidationErrorDTO> validateEmptyMedicalRiskLimitLevel
            (AgreementDTO agreement) {
        return  agreement.getMedicalRiskLimitLevel() == null
                || agreement.getMedicalRiskLimitLevel().isBlank()
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }
}
