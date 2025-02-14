package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateToInFutureValidationTest {

    private final AgreementDateToInFutureValidation agreementDateToInFutureValidation
            = new AgreementDateToInFutureValidation();

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateToInFutureValidation
                .execute(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateToIsInThePast() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now().minusDays(1));
        var errorOptional = agreementDateToInFutureValidation
                .execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateTo", errorOptional.get().getField());
        assertEquals("Must not be in the past!", errorOptional.get().getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateToInFutureValidation.execute(requestMock));
    }

}