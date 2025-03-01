package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonLastNameValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonLastNameValidation personLastNameValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenLastNameIsValid() {
        when(requestMock.getPersonLastName()).thenReturn("Ivanov");
        var errorOptional = personLastNameValidation.execute(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        when(requestMock.getPersonLastName()).thenReturn(null);
        when(validationErrorFactory.getValidationError("ERROR_CODE_2"))
                .thenReturn(new ValidationError("ERROR_CODE_2", "Description"));
        var errorOptional = personLastNameValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_2", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        when(requestMock.getPersonLastName()).thenReturn("");
        when(validationErrorFactory.getValidationError("ERROR_CODE_2"))
                .thenReturn(new ValidationError("ERROR_CODE_2", "Description"));
        var errorOptional = personLastNameValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_2", errorOptional.get().getErrorCode());
        assertEquals("Description", errorOptional.get().getDescription());
    }

}