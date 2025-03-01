package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateToInFutureValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateToInFutureValidation agreementDateToInFutureValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateToInFutureValidation
                .execute(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateToIsInThePast() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now().minusDays(1));
        when(validationErrorFactory.getValidationError("ERROR_CODE_7"))
                .thenReturn(new ValidationError("ERROR_CODE_7", "Description"));
        var errorOptional = agreementDateToInFutureValidation
                .execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_7", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateToInFutureValidation.execute(requestMock));
    }

}