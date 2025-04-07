package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
class EmptyPersonBirthDateValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @InjectMocks
    private EmptyPersonBirthDateValidation emptyPersonBirthDateValidation;

    @Mock
    private TravelCalculatePremiumRequestV1 requestMock;

    @Test
    void shouldNotReturnErrorWhenDateFromIsValid() {
        when(requestMock.getPersonBirthDate())
                .thenReturn(LocalDate.now());
        var errorOpt = emptyPersonBirthDateValidation.validate(requestMock);
        assertTrue(errorOpt.isEmpty());
        Mockito.verifyNoInteractions(errorFactoryMock);
    }

    @Test
    void shouldReturnErrorWhenDateFromIsNull() {
        when(requestMock.getPersonBirthDate())
                .thenReturn(null);
        when(errorFactoryMock.buildError("ERROR_CODE_12"))
                .thenReturn(new ValidationError("ERROR_CODE_12", "description"));
        var errorOpt = emptyPersonBirthDateValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_12", errorOpt.get().errorCode());
        assertEquals("description", errorOpt.get().description());
    }

}