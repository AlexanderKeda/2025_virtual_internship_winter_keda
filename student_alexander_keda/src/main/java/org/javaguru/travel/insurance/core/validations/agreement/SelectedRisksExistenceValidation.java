package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.TravelRequestValidation;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SelectedRisksExistenceValidation implements TravelRequestValidation {

    private final ValidationErrorFactory validationErrorFactory;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public List<ValidationError> validateList(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                ? validateSelectedRisksExistence(request)
                : List.of();
    }

    private List<ValidationError> validateSelectedRisksExistence(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks().stream()
                .filter(Predicate.not(this::doesRiskExist))
                .map(this::buildRiskNotFoundError)
                .toList();
    }

    private boolean doesRiskExist(String riskIc) {
        return classifierValueRepository.existsByClassifierTitleAndIc("RISK_TYPE", riskIc);
    }

    private ValidationError buildRiskNotFoundError(String riskIc) {
        return validationErrorFactory
                .buildError(
                        "ERROR_CODE_9",
                        List.of(new Placeholder("NOT_EXISTING_RISK", riskIc))
                );
    }
}
