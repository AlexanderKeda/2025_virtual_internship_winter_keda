package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PersonFirstNameValidationTest {

    private final PersonFirstNameValidation personFirstNameValidation
            = new PersonFirstNameValidation();

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        when(requestMock.getPersonFirstName()).thenReturn("Ivan");
        var errorOptional = personFirstNameValidation.execute(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        when(requestMock.getPersonFirstName()).thenReturn(null);
        var errorOptional = personFirstNameValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("personFirstName", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        when(requestMock.getPersonFirstName()).thenReturn("");
        var errorOptional = personFirstNameValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("personFirstName", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

}