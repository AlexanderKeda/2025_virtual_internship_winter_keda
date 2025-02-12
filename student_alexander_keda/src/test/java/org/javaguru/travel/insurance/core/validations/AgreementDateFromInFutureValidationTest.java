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
class AgreementDateFromInFutureValidationTest {

    private AgreementDateFromInFutureValidation agreementDateFromInFutureValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        agreementDateFromInFutureValidation = new AgreementDateFromInFutureValidation();
    }

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        Optional<ValidationError> errorOptional = agreementDateFromInFutureValidation
                .execute(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateFromIsInThePast() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now().minusDays(1));
        Optional<ValidationError> errorOptional = agreementDateFromInFutureValidation
                .execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateFrom", errorOptional.get().getField());
        assertEquals("Must not be in the past!", errorOptional.get().getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenDateFromIsNull() {
        when(requestMock.getAgreementDateFrom()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateFromInFutureValidation.execute(requestMock));
    }

}