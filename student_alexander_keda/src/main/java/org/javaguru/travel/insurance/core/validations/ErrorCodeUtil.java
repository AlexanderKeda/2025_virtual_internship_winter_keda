package org.javaguru.travel.insurance.core.validations;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
class ErrorCodeUtil {

    private final Properties errorDescriptions;

    ErrorCodeUtil() throws IOException {
        errorDescriptions = PropertiesLoaderUtils.loadAllProperties("errorCodes.properties");
    }

    String getErrorDescription(String errorCode) {
        return errorDescriptions.containsKey(errorCode)
                ? errorDescriptions.getProperty(errorCode)
                : "Unknown error code!";
    }
}
