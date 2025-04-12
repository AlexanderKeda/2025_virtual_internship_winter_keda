package org.javaguru.travel.insurance.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class PersonDTO {

    private String personFirstName;

    private String personLastName;

    private LocalDate personBirthDate;

    @Setter
    private List<RiskDTO> risks;

    public PersonDTO(
            String personFirstName,
            String personLastName,
            LocalDate personBirthDate
    ) {
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.personBirthDate = personBirthDate;
    }

}
