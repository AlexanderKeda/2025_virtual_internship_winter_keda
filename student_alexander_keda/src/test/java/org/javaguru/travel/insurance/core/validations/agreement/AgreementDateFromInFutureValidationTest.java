package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
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
class AgreementDateFromInFutureValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateFromInFutureValidation agreementDateFromInFutureValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateFromInFutureValidation
                .validate(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsInThePast() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now().minusDays(1));
        when(validationErrorFactory.buildError("ERROR_CODE_6"))
                .thenReturn(new ValidationError("ERROR_CODE_6", "Description"));
        var errorOptional = agreementDateFromInFutureValidation
                .validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_6", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateFromIsNull() {
        when(requestMock.getAgreementDateFrom()).thenReturn(null);
        assertDoesNotThrow(() -> agreementDateFromInFutureValidation.validate(requestMock));
    }

}