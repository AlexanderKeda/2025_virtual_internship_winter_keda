package org.javaguru.travel.insurance.core.api.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonDTO(
        String personFirstName,
        String personLastName,
        LocalDate personBirthDate,
        String medicalRiskLimitLevel, List<RiskDTO> risks
) {

    public PersonDTO {
        risks = risks != null
                ? List.copyOf(risks)
                : null;
    }

    public PersonDTO(String personFirstName,
                     String personLastName,
                     LocalDate personBirthDate,
                     String medicalRiskLimitLevel) {
        this(personFirstName, personLastName, personBirthDate, medicalRiskLimitLevel, List.of());
    }

    public PersonDTO withRisks(List<RiskDTO> risks) {
        return new PersonDTO(
                this.personFirstName,
                this.personLastName,
                this.personBirthDate,
                this.medicalRiskLimitLevel,
                risks
        );
    }

}
