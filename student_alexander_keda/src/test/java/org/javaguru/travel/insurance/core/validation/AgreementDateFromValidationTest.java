package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateFromValidationTest {

    private AgreementDateFromValidation agreementDateFromValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        agreementDateFromValidation = new AgreementDateFromValidation();
    }

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        Optional<ValidationError> errorOptional = agreementDateFromValidation.validateDateFrom(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        when(requestMock.getAgreementDateFrom()).thenReturn(null);
        Optional<ValidationError> errorOptional = agreementDateFromValidation.validateDateFrom(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateFrom", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

}