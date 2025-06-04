package org.javaguru.travel.insurance.core.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AgreementDTOTest {

    @Test
    void shouldReturnNewAgreementDTO() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertNotSame(agreement, updatedAgreement);
    }

    @Test
    void shouldReturnWithTheSameDateFrom() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertEquals(agreement.agreementDateFrom(),
                updatedAgreement.agreementDateFrom());
    }

    @Test
    void shouldReturnWithTheSameDateTo() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertEquals(agreement.agreementDateTo(),
                updatedAgreement.agreementDateTo());
    }

    @Test
    void shouldReturnWithTheSameCountry() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertEquals(agreement.country(),
                updatedAgreement.country());
    }

    @Test
    void shouldReturnWithTheSameSelectedRisks() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertEquals(agreement.selectedRisks(),
                updatedAgreement.selectedRisks());
    }


    @Test
    void shouldReturnWithNewPersons() {
        var agreement = getCorrectAgreementDTO();
        var person1 = getCorrectPersonDTO();
        var person2 = getCorrectPersonDTO();
        var persons = List.of(person1, person2);
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(persons, premium, UUID.randomUUID());
        assertEquals(persons,
                updatedAgreement.persons());
    }

    @Test
    void shouldReturnWithNewAgreementPremium() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertEquals(premium,
                updatedAgreement.agreementPremium());
    }

    @Test
    void shouldReturnWithNewUuid() {
        var agreement = getCorrectAgreementDTO();
        var person = getCorrectPersonDTO();
        var premium = BigDecimal.ONE;
        var updatedAgreement = agreement.withPersonsAndPremiumAndUuid(List.of(person), premium, UUID.randomUUID());
        assertNotEquals(agreement.uuid(),
                updatedAgreement.uuid());
    }

    private AgreementDTO getCorrectAgreementDTO() {
        return new AgreementDTO(
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Latvia",
                List.of("risk1", "risk2"),
                List.of()
        );
    }

    private PersonDTO getCorrectPersonDTO() {
        return new PersonDTO(
                "first",
                "last",
                "code",
                LocalDate.now(),
                "LimitLevel"
        );
    }
}