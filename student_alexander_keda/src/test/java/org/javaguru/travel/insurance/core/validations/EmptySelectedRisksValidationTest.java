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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptySelectedRisksValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptySelectedRisksValidation emptyRisksValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenRisksIsNotEmpty() {
        when(requestMock.getSelectedRisks()).thenReturn(List.of("risk1", "risk2"));
        var errorOptional = emptyRisksValidation.execute(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenRisksIsEmpty() {
        when(requestMock.getSelectedRisks()).thenReturn(List.of());
        when(validationErrorFactory.buildError("ERROR_CODE_5"))
                .thenReturn(new ValidationError("ERROR_CODE_5", "Description"));
        var errorOptional = emptyRisksValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_5", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

    @Test
    void shouldReturnErrorWhenRisksIsNull() {
        when(requestMock.getSelectedRisks()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_5"))
                .thenReturn(new ValidationError("ERROR_CODE_5", "Description"));
        var errorOptional = emptyRisksValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_5", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }
}