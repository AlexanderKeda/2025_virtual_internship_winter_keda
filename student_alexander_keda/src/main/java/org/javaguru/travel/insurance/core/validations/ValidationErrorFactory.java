package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    ValidationError getValidationError(String errorCode) {
        return new ValidationError(errorCode, errorCodeUtil.getErrorDescription(errorCode));
    }
}
