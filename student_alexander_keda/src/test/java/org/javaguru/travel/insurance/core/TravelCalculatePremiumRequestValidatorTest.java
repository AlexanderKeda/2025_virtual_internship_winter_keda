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
    private final String firstName = "FirstName";
    private final String lastName = "LastName";
    private final long days = 7;
    private final LocalDate date1 = LocalDate.now();
    private final LocalDate date2 = date1.plusDays(days);

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
                .agreementDateFrom(null)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertEquals(3, errors.size());

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



    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName(lastName)
                .agreementDateFrom(null)
                .agreementDateTo(date2)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("agreementDateFrom", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }



    @Test
    void shouldReturnErrorWhenDateToIsNull() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(null)
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("agreementDateTo", errors.getFirst().getField());
        assertEquals("Must not be empty!", errors.getFirst().getMessage());
    }

    @Test
    void shouldReturnErrorWhenDateToIsBeforeDateFrom() {
        request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName(lastName)
                .agreementDateFrom(LocalDate.now().plusDays(3))
                .agreementDateTo(LocalDate.now())
                .build();
        errors = requestValidator.validate(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertEquals("agreementDateTo", errors.getFirst().getField());
        assertEquals("Must be after DataFrom!", errors.getFirst().getMessage());
    }

}