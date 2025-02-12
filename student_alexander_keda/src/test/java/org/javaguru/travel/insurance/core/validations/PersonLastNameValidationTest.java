package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonLastNameValidationTest {

    private PersonLastNameValidation personLastNameValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        personLastNameValidation = new PersonLastNameValidation();
    }

    @Test
    void shouldNotReturnErrorWhenLastNameIsValid() {
        when(requestMock.getPersonLastName()).thenReturn("Ivanov");
        Optional<ValidationError> errorOptional = personLastNameValidation.execute(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        when(requestMock.getPersonLastName()).thenReturn(null);
        Optional<ValidationError> errorOptional = personLastNameValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("personLastName", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        when(requestMock.getPersonLastName()).thenReturn("");
        Optional<ValidationError> errorOptional = personLastNameValidation.execute(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("personLastName", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

}