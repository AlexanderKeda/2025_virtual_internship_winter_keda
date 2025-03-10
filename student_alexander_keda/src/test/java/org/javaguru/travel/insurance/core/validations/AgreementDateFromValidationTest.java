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
class AgreementDateFromValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateFromValidation agreementDateFromValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        var errorOptional = agreementDateFromValidation.validate(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        when(requestMock.getAgreementDateFrom()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_3"))
                .thenReturn(new ValidationError("ERROR_CODE_3", "Description"));
        var errorOptional = agreementDateFromValidation.validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_3", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

}