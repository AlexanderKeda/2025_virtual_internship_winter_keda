package org.javaguru.travel.insurance.core.validations;

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
class AgreementDateToValidationTest {

    private AgreementDateToValidation agreementDateToValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        agreementDateToValidation = new AgreementDateToValidation();
    }

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());
        Optional<ValidationError> errorOptional = agreementDateToValidation.validateDateTo(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        Optional<ValidationError> errorOptional = agreementDateToValidation.validateDateTo(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateTo", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }
}