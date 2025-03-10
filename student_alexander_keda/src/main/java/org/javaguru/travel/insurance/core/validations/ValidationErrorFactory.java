package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.ErrorCodeUtil;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    ValidationError buildError(String errorCode) {
        return new ValidationError(errorCode, errorCodeUtil.getErrorDescription(errorCode));
    }

    ValidationError buildError(String errorCode, List<Placeholder> placeholders) {
        return new ValidationError(errorCode, errorCodeUtil.getErrorDescription(errorCode, placeholders));
    }
}
