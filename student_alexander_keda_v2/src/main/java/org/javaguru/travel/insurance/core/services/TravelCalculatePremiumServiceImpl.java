package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final AgreementPremiumCalculator agreementPremiumCalculator;
    private final TravelAgreementValidator agreementValidator;

    @Override
    public TravelCalculatePremiumCoreResult calculatePremium(TravelCalculatePremiumCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementValidator.validate(command.agreement());
        return errors.isEmpty()
                ? buildCoreResult(command.agreement())
                : buildCoreResult(errors);
    }

    private TravelCalculatePremiumCoreResult buildCoreResult(List<ValidationErrorDTO> errors) {
        return new TravelCalculatePremiumCoreResult(errors);
    }

    private TravelCalculatePremiumCoreResult buildCoreResult(AgreementDTO agreement) {
        AgreementDTO updatedAgreement = agreementPremiumCalculator.calculateAgreementPremiums(agreement);
        return new TravelCalculatePremiumCoreResult(updatedAgreement);
    }

}
