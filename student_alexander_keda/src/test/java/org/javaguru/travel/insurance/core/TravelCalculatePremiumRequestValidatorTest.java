package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TravelCalculatePremiumRequestValidatorTest {

    private TravelCalculatePremiumRequestValidator requestValidator;
    private TravelCalculatePremiumRequest request;
    private List<ValidationError> errors;
    private String firstName = "FirstName";
    private String lastName = "LastName";
    private long days = 7;
    private LocalDate date1 = LocalDate.now();
    private LocalDate date2 = date1.plusDays(days);

    @BeforeEach
    void setUp() {
        requestValidator = new TravelCalculatePremiumRequestValidator();
    }

    @Test
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnNonEmptyErrorListWhenFirstNameIsNull() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(null)
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @Test
    void shouldReturnNonEmptyErrorListWhenFirstNameIsEmpty() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName("")
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
    }

    @Test
    void shouldReturnCorrectErrorCountWhenFirstNameIsEmpty() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName("")
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertEquals(1, errors.size());
    }

    @Test
    void shouldReturnCorrectErrorAndMessageWhenFirstNameIsEmpty() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName("")
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertEquals("personFirstName", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }

}