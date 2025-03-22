package org.javaguru.travel.insurance.core.validations;

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
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "RISK_1"))
                .thenReturn(true);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "RISK_2"))
                .thenReturn(true);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "RISK_3"))
                .thenReturn(true);
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
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK"))
                .thenReturn(false);
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
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK_1"))
                .thenReturn(false);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK_2"))
                .thenReturn(false);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "FAKE_RISK_3"))
                .thenReturn(false);
        when(classifierValueRepositoryMock.existsByClassifierTitleAndIc("RISK_TYPE", "RISK_1"))
                .thenReturn(true);
        when(validationErrorFactoryMock
                .buildError(Mockito.eq("ERROR_CODE_9"), ArgumentMatchers.anyList()))
                .thenReturn(new ValidationError("", ""));
        List<ValidationError> errors = selectedRisksExistenceValidation.validateList(request);
        assertFalse(errors.isEmpty());
        assertEquals(3, errors.size());
    }
}