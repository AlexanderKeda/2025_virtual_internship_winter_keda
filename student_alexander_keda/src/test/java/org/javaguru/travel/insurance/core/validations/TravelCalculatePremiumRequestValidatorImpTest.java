package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumRequestValidatorImpTest {

    @Mock
    private TravelRequestValidation validation1Mock;
    @Mock
    private TravelRequestValidation validation2Mock;

    private TravelCalculatePremiumRequestValidatorImp requestValidator;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        var validations = List.of(validation1Mock, validation2Mock);
        requestValidator = new TravelCalculatePremiumRequestValidatorImp(validations);
    }

    @Test
    void shouldNotReturnErrors() {
        when(validation1Mock.validate(requestMock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validate(requestMock))
                .thenReturn(Optional.empty());

        var errors = requestValidator.validate(requestMock);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(validation1Mock.validate(requestMock))
                .thenReturn(Optional.of(new ValidationError()));
        when(validation2Mock.validate(requestMock))
                .thenReturn(Optional.of(new ValidationError()));

        var errors = requestValidator.validate(requestMock);
        assertEquals(2, errors.size());
    }

}