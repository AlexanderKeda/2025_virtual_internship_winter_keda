package org.javaguru.travel.insurance.core.api.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonDTO (
        String personFirstName,
        String personLastName,
        LocalDate personBirthDate,
        List<RiskDTO> risks) {

    public PersonDTO {
        risks = List.copyOf(risks);
    }

    public PersonDTO(String personFirstName,
                     String personLastName,
                     LocalDate personBirthDate) {
        this(personFirstName, personLastName, personBirthDate, List.of());
    }

    public PersonDTO withRisks(List<RiskDTO> risks) {
        return new PersonDTO(
                this.personFirstName,
                this.personLastName,
                this.personBirthDate,
                risks
        );
    }

}
