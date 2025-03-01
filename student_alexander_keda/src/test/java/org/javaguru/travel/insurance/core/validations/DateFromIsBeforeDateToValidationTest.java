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
class DateFromIsBeforeDateToValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private DateFromIsBeforeDateToValidation dateFromIsBeforeDateToValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsBeforeDateTo() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));

        var errorOptional = dateFromIsBeforeDateToValidation
                .execute(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsAfterDateTo() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now().plusDays(1));
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());
        when(validationErrorFactory.buildError("ERROR_CODE_8"))
                .thenReturn(new ValidationError("ERROR_CODE_8", "Description"));
        var errorOptional = dateFromIsBeforeDateToValidation
                .execute(requestMock);

        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_8", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

    @Test
    void shouldNotThrowExceptionWhenDateFromIsNull() {
        when(requestMock.getAgreementDateFrom()).thenReturn(null);
        assertDoesNotThrow(() -> dateFromIsBeforeDateToValidation.execute(requestMock));
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> dateFromIsBeforeDateToValidation.execute(requestMock));
    }

}