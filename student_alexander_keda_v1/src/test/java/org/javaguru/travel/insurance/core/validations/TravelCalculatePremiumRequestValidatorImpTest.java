package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
    private TravelCalculatePremiumRequestV1 requestMock;

    @BeforeEach
    void setUp() {
        var validations = List.of(validation1Mock, validation2Mock);
        requestValidator = new TravelCalculatePremiumRequestValidatorImp(validations);
    }

    @Test
    void shouldNotReturnErrors() {
        when(validation1Mock.validate(requestMock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(requestMock))
                .thenReturn(List.of());
        when(validation2Mock.validate(requestMock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(requestMock))
                .thenReturn(List.of());

        var errors = requestValidator.validate(requestMock);
        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnSingleErrors() {
        when(validation1Mock.validate(requestMock))
                .thenReturn(Optional.of(new ValidationError("", "")));
        when(validation1Mock.validateList(requestMock))
                .thenReturn(List.of());
        when(validation2Mock.validate(requestMock))
                .thenReturn(Optional.of(new ValidationError("", "")));
        when(validation2Mock.validateList(requestMock))
                .thenReturn(List.of());

        var errors = requestValidator.validate(requestMock);
        assertEquals(2, errors.size());
    }

    @Test
    void shouldReturnListErrors() {
        when(validation1Mock.validate(requestMock))
                .thenReturn(Optional.empty());
        when(validation1Mock.validateList(requestMock))
                .thenReturn(List.of(new ValidationError("", ""), new ValidationError("", "")));
        when(validation2Mock.validate(requestMock))
                .thenReturn(Optional.empty());
        when(validation2Mock.validateList(requestMock))
                .thenReturn(List.of(new ValidationError("", "")));

        var errors = requestValidator.validate(requestMock);
        assertEquals(3, errors.size());
    }

    @Test
    void shouldReturnExpectedErrorCount() {
        when(validation1Mock.validate(requestMock))
                .thenReturn(Optional.of(new ValidationError("", "")));
        when(validation1Mock.validateList(requestMock))
                .thenReturn(List.of(new ValidationError("", ""), new ValidationError("", "")));
        when(validation2Mock.validate(requestMock))
                .thenReturn(Optional.of(new ValidationError("", "")));
        when(validation2Mock.validateList(requestMock))
                .thenReturn(List.of(new ValidationError("", "")));

        var errors = requestValidator.validate(requestMock);
        assertEquals(5, errors.size());
    }

}