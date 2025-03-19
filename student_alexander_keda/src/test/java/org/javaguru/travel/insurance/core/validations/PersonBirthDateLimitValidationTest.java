package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.util.AgeCalculator;
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
class PersonBirthDateLimitValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private AgeCalculator ageCalculator;

    @InjectMocks
    private PersonBirthDateLimitValidation personBirthDateLimitValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldNotReturnErrorWhenBirthDateIsValid() {
        LocalDate birthDate = LocalDate.now().minusYears(20);
        when(requestMock.getPersonBirthDate()).thenReturn(birthDate);
        when(ageCalculator.calculate(birthDate)).thenReturn(20L);
        var errorOpt = personBirthDateLimitValidation
                .validate(requestMock);
        assertTrue(errorOpt.isEmpty());
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenAgeExceedsTheLimit() {
        LocalDate birthDate = LocalDate.now().minusYears(151);
        when(requestMock.getPersonBirthDate()).thenReturn(birthDate);
        when(ageCalculator.calculate(birthDate)).thenReturn(151L);
        when(errorFactoryMock.buildError("ERROR_CODE_14"))
                .thenReturn(new ValidationError("ERROR_CODE_14", "Description"));
        var errorOpt = personBirthDateLimitValidation
                .validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_14", errorOpt.get().errorCode());
        assertEquals("Description", errorOpt.get().description());
    }

    @Test
    void shouldNotThrowExceptionWhenDateToIsNull() {
        when(requestMock.getPersonBirthDate()).thenReturn(null);
        assertDoesNotThrow(() -> personBirthDateLimitValidation.validate(requestMock));
    }

}