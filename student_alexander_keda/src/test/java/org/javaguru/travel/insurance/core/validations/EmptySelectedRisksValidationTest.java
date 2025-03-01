package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptySelectedRisksValidationTest {

    private final EmptySelectedRisksValidation emptyRisksValidation
            = new EmptySelectedRisksValidation();

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenRisksIsNotEmpty() {
        when(requestMock.getSelectedRisks()).thenReturn(List.of("risk1", "risk2"));
        var errorOptional = emptyRisksValidation.execute(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenRisksIsEmpty() {
        when(requestMock.getSelectedRisks()).thenReturn(List.of());
        var errorOptional = emptyRisksValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("selectedRisks", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());

    }

    @Test
    void shouldReturnErrorWhenRisksIsNull() {
        when(requestMock.getSelectedRisks()).thenReturn(null);
        var errorOptional = emptyRisksValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("selectedRisks", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());

    }
}