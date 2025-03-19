package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonBirthDateInPastValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private PersonBirthDateInPastValidation personBirthDateInPastValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenBirthDateIsValid() {
        when(requestMock.getPersonBirthDate()).thenReturn(LocalDate.now().minusYears(20));
        var errorOpt = personBirthDateInPastValidation
                .validate(requestMock);
        assertTrue(errorOpt.isEmpty());
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenBirthDateIsInTheFuture() {
        when(requestMock.getPersonBirthDate()).thenReturn(LocalDate.now().plusYears(1));
        when(errorFactoryMock.buildError("ERROR_CODE_13"))
                .thenReturn(new ValidationError("ERROR_CODE_13", "Description"));
        var errorOpt = personBirthDateInPastValidation
                .validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_13", errorOpt.get().errorCode());
        assertEquals("Description", errorOpt.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getPersonBirthDate()).thenReturn(null);
        assertDoesNotThrow(() -> personBirthDateInPastValidation.validate(requestMock));
    }

}