package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumRequestValidatorTest {

    @Mock
    private TravelRequestValidation validation1Mock;
    @Mock
    private TravelRequestValidation validation2Mock;

    private TravelCalculatePremiumRequestValidator requestValidator;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    private List<ValidationError> errors;

    @Test
    void shouldNotReturnErrors() {
        List<TravelRequestValidation> validations = List.of(validation1Mock, validation2Mock);
        requestValidator = new TravelCalculatePremiumRequestValidator(validations);
        when(validation1Mock.execute(requestMock))
                .thenReturn(Optional.empty());
        when(validation2Mock.execute(requestMock))
                .thenReturn(Optional.empty());

        errors = requestValidator.validate(requestMock);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        List<TravelRequestValidation> validations = List.of(validation1Mock, validation2Mock);
        requestValidator = new TravelCalculatePremiumRequestValidator(validations);
        when(validation1Mock.execute(requestMock))
                .thenReturn(Optional.of(new ValidationError()));
        when(validation2Mock.execute(requestMock))
                .thenReturn(Optional.of(new ValidationError()));


        errors = requestValidator.validate(requestMock);
        assertEquals(2, errors.size());
    }

}