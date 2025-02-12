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
class AgreementDateToInFutureValidationTest {

    private AgreementDateToInFutureValidation agreementDateToInFutureValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        agreementDateToInFutureValidation = new AgreementDateToInFutureValidation();
    }

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());
        Optional<ValidationError> errorOptional = agreementDateToInFutureValidation
                .validateDateToInFuture(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateToIsInThePast() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now().minusDays(1));
        Optional<ValidationError> errorOptional = agreementDateToInFutureValidation
                .validateDateToInFuture(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateTo", errorOptional.get().getField());
        assertEquals("Must not be in the past!", errorOptional.get().getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateToInFutureValidation.validateDateToInFuture(requestMock));
    }

}