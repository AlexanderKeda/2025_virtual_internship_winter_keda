package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
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
class PersonFirstNameValidationTest {

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonFirstNameValidation personFirstNameValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        when(requestMock.getPersonFirstName()).thenReturn("Ivan");
        var errorOptional = personFirstNameValidation.validate(requestMock);
        assertTrue(errorOptional.isEmpty());
        Mockito.verifyNoInteractions(validationErrorFactory);
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        when(requestMock.getPersonFirstName()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationError("ERROR_CODE_1", "Description"));
        var errorOptional = personFirstNameValidation.validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_1", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        when(requestMock.getPersonFirstName()).thenReturn("");
        when(validationErrorFactory.buildError("ERROR_CODE_1"))
                .thenReturn(new ValidationError("ERROR_CODE_1", "Description"));
        var errorOptional = personFirstNameValidation.validate(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("ERROR_CODE_1", errorOptional.get().errorCode());
        assertEquals("Description", errorOptional.get().description());
    }

}