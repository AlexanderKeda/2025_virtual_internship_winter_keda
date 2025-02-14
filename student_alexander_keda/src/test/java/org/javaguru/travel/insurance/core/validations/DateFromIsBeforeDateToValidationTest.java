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
class DateFromIsBeforeDateToValidationTest {

    private final DateFromIsBeforeDateToValidation dateFromIsBeforeDateToValidation
            = new DateFromIsBeforeDateToValidation();

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsBeforeDateTo() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));

        var errorOptional = dateFromIsBeforeDateToValidation
                .execute(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateFromIsAfterDateTo() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now().plusDays(1));
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());

        var errorOptional = dateFromIsBeforeDateToValidation
                .execute(requestMock);

        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateTo", errorOptional.get().getField());
        assertEquals("Must be after DataFrom!", errorOptional.get().getMessage());
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