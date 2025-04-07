package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyMedicalRiskLimitLevelValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private TravelCalculatePremiumRequest requestMock;


    @Test
    void shouldSucceedWhenLimitLevelDisabled() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                false,
                errorFactoryMock
        );
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
        Mockito.verifyNoInteractions(requestMock);
    }

    @Test
    void shouldSucceedWhenLimitLevelIsNotEmptyAndHasRequiredRisks() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn("LIMIT_LEVEL");
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreMissing() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("FAKE_RISK"));
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreNull() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(null);
        assertEquals(Optional.empty(), emptyLimitLevelValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenLimitLevelIsEmptyAndHasRequiredRisks() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn("");
        when(errorFactoryMock.buildError("ERROR_CODE_15"))
                .thenReturn(new ValidationError("", ""));
        var errorOpt = emptyLimitLevelValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenLimitLevelIsNullAndHasRequiredRisks() {
        var emptyLimitLevelValidation = new EmptyMedicalRiskLimitLevelValidation(
                true,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_15"))
                .thenReturn(new ValidationError("", ""));
        var errorOpt = emptyLimitLevelValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

}