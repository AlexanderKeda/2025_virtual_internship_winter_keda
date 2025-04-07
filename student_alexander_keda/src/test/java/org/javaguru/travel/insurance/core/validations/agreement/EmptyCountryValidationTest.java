package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private EmptyCountryValidation emptyCountryValidation;

    @Mock
    private TravelCalculatePremiumRequestV1 requestMock;

    @Test
    void shouldSucceedWhenCountryIsNotEmpty() {
        when(requestMock.getCountry())
                .thenReturn("LATVIA");
        assertEquals(Optional.empty(), emptyCountryValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsEmpty() {
        when(requestMock.getCountry())
                .thenReturn("");
        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationError("", ""));
        Optional<ValidationError> errorOpt = emptyCountryValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenCountryIsNull() {
        when(requestMock.getCountry())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationError("", ""));
        Optional<ValidationError> errorOpt = emptyCountryValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

}