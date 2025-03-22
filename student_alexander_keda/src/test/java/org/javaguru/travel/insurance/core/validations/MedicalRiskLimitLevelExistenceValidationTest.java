package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
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
class MedicalRiskLimitLevelExistenceValidationTest {

    @Mock
    private ClassifierValueRepository classifierValueRepositoryMock;

    @Mock
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private TravelCalculatePremiumRequest requestMock;


    @Test
    void shouldSucceedWhenLimitLevelDisabled() {
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                false,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);
        Mockito.verifyNoInteractions(requestMock);
    }

    @Test
    void shouldSucceedWhenLimitLevelExistAndHasRequiredRisks() {
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn("LIMIT_LEVEL");
        when(classifierValueRepositoryMock
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LIMIT_LEVEL"))
                .thenReturn(true);
        when(medicalRiskLimitLevelRepository
                .existsByMedicalRiskLimitIc("LIMIT_LEVEL"))
                .thenReturn(true);
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreMissing() {
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("FAKE_RISK"));
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreNull() {
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(null);
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldSucceedWhenLimitLevelIsNullAndHasRequiredRisks() {
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn(null);
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);

    }

    @Test
    void shouldSucceedWhenLimitLevelIsEmptyAndHasRequiredRisks() {
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn("");
        assertEquals(Optional.empty(), limitLevelExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
        Mockito.verifyNoInteractions(errorFactoryMock);

    }

    @Test
    void shouldReturnErrorWhenLimitLevelIcIsNotExistAndHasRequiredRisks() {
        String limitLevel = "FAKE_LIMIT_LEVEL";
        var placeholder = new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel);
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn(limitLevel);
        when(classifierValueRepositoryMock
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel))
                .thenReturn(false);
        when(errorFactoryMock.buildError(
                "ERROR_CODE_16",
                List.of(placeholder)
        )).thenReturn(new ValidationError("", ""));
        var errorOpt = limitLevelExistenceValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepository);
    }

    @Test
    void shouldReturnErrorWhenLimitLevelCoefficientIsNotExistAndHasRequiredRisks() {
        String limitLevel = "LIMIT_LEVEL";
        var placeholder = new Placeholder("NOT_EXISTING_RISK_LEVEL", limitLevel);
        var limitLevelExistenceValidation = new MedicalRiskLimitLevelExistenceValidation(
                true,
                classifierValueRepositoryMock,
                medicalRiskLimitLevelRepository,
                errorFactoryMock
        );
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getMedicalRiskLimitLevel())
                .thenReturn(limitLevel);
        when(classifierValueRepositoryMock
                .existsByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", limitLevel))
                .thenReturn(true);
        when(medicalRiskLimitLevelRepository
                .existsByMedicalRiskLimitIc(limitLevel))
                .thenReturn(false);
        when(errorFactoryMock.buildError(
                "ERROR_CODE_16",
                List.of(placeholder)
        )).thenReturn(new ValidationError("", ""));
        var errorOpt = limitLevelExistenceValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());

    }

}