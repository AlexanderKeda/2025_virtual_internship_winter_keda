package org.javaguru.travel.insurance.core.api.command;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;

public record TravelGetAllAgreementUuidsCoreResult(List<ValidationErrorDTO> errors, List<String> agreementUuids) {

    public TravelGetAllAgreementUuidsCoreResult(List<ValidationErrorDTO> errors) {
        this(errors, null);
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
}
