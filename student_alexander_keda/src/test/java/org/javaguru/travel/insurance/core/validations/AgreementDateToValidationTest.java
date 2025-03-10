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
class AgreementDateToValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateToValidation agreementDateToValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateToIsValid() {
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateToValidation.validate(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_4"))
                .thenReturn(new ValidationError("ERROR_CODE_4", "Description"));
        var errorOptional = agreementDateToValidation.validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_4", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

}