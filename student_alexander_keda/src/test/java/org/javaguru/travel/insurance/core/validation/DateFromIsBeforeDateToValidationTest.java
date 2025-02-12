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
class DateFromIsBeforeDateToValidationTest {

    private DateFromIsBeforeDateToValidation dateFromIsBeforeDateToValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        dateFromIsBeforeDateToValidation = new DateFromIsBeforeDateToValidation();
    }

    @Test
    void shouldNotReturnErrorWhenDateFromIsBeforeDateTo() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));

        Optional<ValidationError> errorOptional = dateFromIsBeforeDateToValidation
                .validateDateFromIsBeforeDateTo(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenDateFromIsAfterDateTo() {
        when(requestMock.getAgreementDateFrom()).thenReturn(LocalDate.now().plusDays(1));
        when(requestMock.getAgreementDateTo()).thenReturn(LocalDate.now());

        Optional<ValidationError> errorOptional = dateFromIsBeforeDateToValidation
                .validateDateFromIsBeforeDateTo(requestMock);

        assertTrue(errorOptional.isPresent());
        assertEquals("agreementDateTo", errorOptional.get().getField());
        assertEquals("Must be after DataFrom!", errorOptional.get().getMessage());
    }

    @Test
    void shouldNotThrowExceptionWhenDateFromIsNull() {
        when(requestMock.getAgreementDateFrom()).thenReturn(null);
        assertDoesNotThrow(() -> dateFromIsBeforeDateToValidation.validateDateFromIsBeforeDateTo(requestMock));
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getAgreementDateTo()).thenReturn(null);
        assertDoesNotThrow(() -> dateFromIsBeforeDateToValidation.validateDateFromIsBeforeDateTo(requestMock));
    }

}