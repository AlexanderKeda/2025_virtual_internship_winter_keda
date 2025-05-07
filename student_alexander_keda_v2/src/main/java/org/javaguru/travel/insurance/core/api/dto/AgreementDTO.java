package org.javaguru.travel.insurance.core.api.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AgreementDTO(LocalDate agreementDateFrom,
                           LocalDate agreementDateTo,
                           String country,
                           String medicalRiskLimitLevel,
                           List<String> selectedRisks,
                           List<PersonDTO> persons,
                           BigDecimal agreementPremium) {

    public AgreementDTO {
        selectedRisks = selectedRisks != null
                ? List.copyOf(selectedRisks)
                : null;
        persons = persons != null
                ? List.copyOf(persons)
                : null;
    }

    public AgreementDTO(
            LocalDate agreementDateFrom,
            LocalDate agreementDateTo,
            String country,
            String medicalRiskLimitLevel,
            List<String> selectedRisks,
            List<PersonDTO> persons
    ) {
        this(agreementDateFrom,
                agreementDateTo,
                country,
                medicalRiskLimitLevel,
                selectedRisks,
                persons,
                new BigDecimal("-1")
        );
    }

    public AgreementDTO withPersonsAndPremium(List<PersonDTO> persons,
                                              BigDecimal agreementPremium) {
        return new AgreementDTO(
                this.agreementDateFrom,
                this.agreementDateTo,
                this.country,
                this.medicalRiskLimitLevel,
                this.selectedRisks,
                persons,
                agreementPremium
        );
    }
}
