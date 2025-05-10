package org.javaguru.travel.insurance.core.api.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonDTO(
        String personFirstName,
        String personLastName,
        LocalDate personBirthDate,
        List<RiskDTO> risks,
        String medicalRiskLimitLevel) {

    public PersonDTO {
        risks = risks != null
                ? List.copyOf(risks)
                : null;
    }

    public PersonDTO(String personFirstName,
                     String personLastName,
                     LocalDate personBirthDate,
                     String medicalRiskLimitLevel) {
        this(personFirstName, personLastName, personBirthDate, List.of(), medicalRiskLimitLevel);
    }

    public PersonDTO withRisks(List<RiskDTO> risks) {
        return new PersonDTO(
                this.personFirstName,
                this.personLastName,
                this.personBirthDate,
                risks,
                this.medicalRiskLimitLevel
        );
    }

}
