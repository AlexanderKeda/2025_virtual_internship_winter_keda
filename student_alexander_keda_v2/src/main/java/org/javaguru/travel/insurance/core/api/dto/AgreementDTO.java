package org.javaguru.travel.insurance.core.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class AgreementDTO {

    private LocalDate agreementDateFrom;

    private LocalDate agreementDateTo;

    private String country;

    private String medicalRiskLimitLevel;

    private List<String> selectedRisks;

    private List<PersonDTO> persons;

    @Setter
    private BigDecimal agreementPremium;

    public AgreementDTO(
            LocalDate agreementDateFrom,
            LocalDate agreementDateTo,
            String country,
            String medicalRiskLimitLevel,
            List<String> selectedRisks,
            List<PersonDTO> persons
    ) {
        this.agreementDateFrom = agreementDateFrom;
        this.agreementDateTo = agreementDateTo;
        this.country = country;
        this.medicalRiskLimitLevel = medicalRiskLimitLevel;
        this.selectedRisks = selectedRisks;
        this.persons = persons;
    }
}
