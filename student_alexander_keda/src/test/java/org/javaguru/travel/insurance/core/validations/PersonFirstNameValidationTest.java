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
class PersonFirstNameValidationTest {

    private PersonFirstNameValidation personFirstNameValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        personFirstNameValidation = new PersonFirstNameValidation();
    }

    @Test
    void shouldNotReturnErrorWhenFirstNameIsValid() {
        when(requestMock.getPersonFirstName()).thenReturn("Ivan");
        Optional<ValidationError> errorOptional = personFirstNameValidation.validatePersonFirstName(requestMock);
        assertTrue(errorOptional.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsNull() {
        when(requestMock.getPersonFirstName()).thenReturn(null);
        Optional<ValidationError> errorOptional = personFirstNameValidation.validatePersonFirstName(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("personFirstName", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

    @Test
    void shouldReturnErrorWhenFirstNameIsEmpty() {
        when(requestMock.getPersonFirstName()).thenReturn("");
        Optional<ValidationError> errorOptional = personFirstNameValidation.validatePersonFirstName(requestMock);
        assertTrue(errorOptional.isPresent());
        assertEquals("personFirstName", errorOptional.get().getField());
        assertEquals("Must not be empty!", errorOptional.get().getMessage());
    }

}