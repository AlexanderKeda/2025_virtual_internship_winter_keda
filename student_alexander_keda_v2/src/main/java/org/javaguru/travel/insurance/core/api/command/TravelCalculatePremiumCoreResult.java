package org.javaguru.travel.insurance.core.api.command;

import lombok.Getter;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;

@Getter
public class TravelCalculatePremiumCoreResult {

    private List<ValidationErrorDTO> errors;

    private AgreementDTO agreement;

    public TravelCalculatePremiumCoreResult(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }

    public TravelCalculatePremiumCoreResult(AgreementDTO agreement) {
        this.agreement = agreement;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
