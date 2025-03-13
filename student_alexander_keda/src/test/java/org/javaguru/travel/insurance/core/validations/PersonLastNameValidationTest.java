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
        var errorOptional = personLastNameValidation.validate(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenLastNameIsNull() {
        when(requestMock.getPersonLastName()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationError("ERROR_CODE_2", "Description"));
        var errorOptional = personLastNameValidation.validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_2", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldReturnErrorWhenLastNameIsEmpty() {
        when(requestMock.getPersonLastName()).thenReturn("");
        when(validationErrorFactory.buildError("ERROR_CODE_2"))
                .thenReturn(new ValidationError("ERROR_CODE_2", "Description"));
        var errorOptional = personLastNameValidation.validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_2", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

}