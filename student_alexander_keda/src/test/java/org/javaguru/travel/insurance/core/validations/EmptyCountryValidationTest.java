package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldSucceedWhenCountryIsNotEmptyAndHasRequiredRisks() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn("LATVIA");
        assertEquals(Optional.empty(), emptyCountryValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreMissing() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("FAKE_RISK"));
        assertEquals(Optional.empty(), emptyCountryValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreNull() {
        when(requestMock.getSelectedRisks())
                .thenReturn(null);
        assertEquals(Optional.empty(), emptyCountryValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsEmptyAndHasRequiredRisks() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn("");
        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationError("", ""));
        Optional<ValidationError> errorOpt = emptyCountryValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenCountryIsNullAndHasRequiredRisks() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_10"))
                .thenReturn(new ValidationError("", ""));
        Optional<ValidationError> errorOpt = emptyCountryValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

}