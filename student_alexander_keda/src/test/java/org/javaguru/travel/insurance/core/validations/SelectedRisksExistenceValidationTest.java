package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksExistenceValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactoryMock;

    @Mock
    private ClassifierValueRepository classifierValueRepositoryMock;

    @InjectMocks
    private SelectedRisksExistenceValidation selectedRisksExistenceValidation;

    @Mock
    private TravelCalculatePremiumRequest request;

    @Test
    void shouldNotReturnErrorWhenAllRisksExist() {
        when(request.getSelectedRisks()).thenReturn(List.of("RISK_1", "RISK_2", "RISK_3"));
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "RISK_1"))
                .thenReturn(Optional.of(new ClassifierValue()));
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "RISK_2"))
                .thenReturn(Optional.of(new ClassifierValue()));
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "RISK_3"))
                .thenReturn(Optional.of(new ClassifierValue()));
        assertTrue(selectedRisksExistenceValidation.validateList(request).isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
    }

    @Test
    void shouldNotReturnErrorWhenRiskListIsEmpty() {
        when(request.getSelectedRisks()).thenReturn(List.of());
        assertTrue(selectedRisksExistenceValidation.validateList(request).isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldNotReturnErrorWhenRiskListIsNull() {
        when(request.getSelectedRisks()).thenReturn(null);
        assertTrue(selectedRisksExistenceValidation.validateList(request).isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactoryMock);
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldReturnErrorWhenRiskDoesNotExist() {
        when(request.getSelectedRisks()).thenReturn(List.of("FAKE_RISK"));
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK"))
                .thenReturn(Optional.empty());
        when(validationErrorFactoryMock
                .buildError("ERROR_CODE_9", List.of(new Placeholder("NOT_EXISTING_RISK", "FAKE_RISK"))))
                .thenReturn(new ValidationError("", ""));
        List<ValidationError> errors = selectedRisksExistenceValidation.validateList(request);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
    }

    @Test
    void shouldReturnCorrectErrorCountWhenRisksDoNotExist() {
        when(request.getSelectedRisks()).thenReturn(List.of("FAKE_RISK_1", "FAKE_RISK_2", "FAKE_RISK_3", "RISK_1"));
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK_1"))
                .thenReturn(Optional.empty());
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK_2"))
                .thenReturn(Optional.empty());
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK_3"))
                .thenReturn(Optional.empty());
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("RISK_TYPE", "RISK_1"))
                .thenReturn(Optional.of(new ClassifierValue()));
        when(validationErrorFactoryMock
                .buildError(Mockito.eq("ERROR_CODE_9"), ArgumentMatchers.anyList()))
                .thenReturn(new ValidationError("", ""));
        List<ValidationError> errors = selectedRisksExistenceValidation.validateList(request);
        assertFalse(errors.isEmpty());
        assertEquals(3, errors.size());
    }
}