package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.util.ErrorCodeUtil;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationErrorFactoryTest {

    @Mock private ErrorCodeUtil errorCodeUtilMock;
    @InjectMocks private ValidationErrorFactory validationErrorFactory;

    @Test
    void shouldReturnValidationErrorWithCorrectDescription() {
        when(errorCodeUtilMock.getErrorDescription("ERROR_CODE_0")).thenReturn("Description");
        assertEquals(new ValidationError("ERROR_CODE_0", "Description"),
                validationErrorFactory.buildError("ERROR_CODE_0"));
    }

}