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
    void shouldNotReturnErrorWhenRequestIsValid() {
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
    void shouldReturnCorrectErrorCountWhenRequestIsNotValid() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName("")
                .personLastName("")
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertEquals(2, errors.size());

        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName("")
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertEquals(1, errors.size());
    }



    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(null)
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("personFirstName", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName("")
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("personFirstName", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }



    @Test
    void shouldReturnErrorWhenLastNameIsNull() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName(null)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("personLastName", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }

    @Test
    void shouldReturnErrorWhenLastNameIsEmpty() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName("")
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("personLastName", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }

}