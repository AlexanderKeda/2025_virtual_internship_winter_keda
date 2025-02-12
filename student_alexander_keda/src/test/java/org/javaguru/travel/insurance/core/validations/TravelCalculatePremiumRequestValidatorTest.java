package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumRequestValidatorTest {

    @Mock private PersonFirstNameValidation personFirstNameValidationMock;
    @Mock private PersonLastNameValidation personLastNameValidationMock;
    @Mock private AgreementDateFromValidation agreementDateFromValidationMock;
    @Mock private AgreementDateToValidation agreementDateToValidationMock;
    @Mock private AgreementDateFromInFutureValidation agreementDateFromInFutureValidationMock;
    @Mock private AgreementDateToInFutureValidation agreementDateToInFutureValidationMock;
    @Mock private DateFromIsBeforeDateToValidation dateFromIsBeforeDateToValidationMock;

    @InjectMocks
    private TravelCalculatePremiumRequestValidator requestValidator;

    @Mock
    private TravelCalculatePremiumRequest request;

    private List<ValidationError> errors;

    @Test
    void shouldNotReturnErrors() {
        when(personFirstNameValidationMock.validatePersonFirstName(request))
                .thenReturn(Optional.empty());
        when(personLastNameValidationMock.validatePersonLastName(request))
                .thenReturn(Optional.empty());
        when(agreementDateFromValidationMock.validateDateFrom(request))
                .thenReturn(Optional.empty());
        when(agreementDateToValidationMock.validateDateTo(request))
                .thenReturn(Optional.empty());
        when(agreementDateFromInFutureValidationMock.validateDateFromInFuture(request))
                .thenReturn(Optional.empty());
        when(agreementDateToInFutureValidationMock.validateDateToInFuture(request))
                .thenReturn(Optional.empty());
        when(dateFromIsBeforeDateToValidationMock.validateDateFromIsBeforeDateTo(request))
                .thenReturn(Optional.empty());

        errors = requestValidator.validate(request);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(personFirstNameValidationMock.validatePersonFirstName(request))
                .thenReturn(Optional.of(new ValidationError()));
        when(personLastNameValidationMock.validatePersonLastName(request))
                .thenReturn(Optional.of(new ValidationError()));
        when(agreementDateFromValidationMock.validateDateFrom(request))
                .thenReturn(Optional.of(new ValidationError()));
        when(agreementDateToValidationMock.validateDateTo(request))
                .thenReturn(Optional.of(new ValidationError()));
        when(agreementDateFromInFutureValidationMock.validateDateFromInFuture(request))
                .thenReturn(Optional.of(new ValidationError()));
        when(agreementDateToInFutureValidationMock.validateDateToInFuture(request))
                .thenReturn(Optional.of(new ValidationError()));
        when(dateFromIsBeforeDateToValidationMock.validateDateFromIsBeforeDateTo(request))
                .thenReturn(Optional.of(new ValidationError()));

        errors = requestValidator.validate(request);
        assertEquals(7, errors.size());
    }

}