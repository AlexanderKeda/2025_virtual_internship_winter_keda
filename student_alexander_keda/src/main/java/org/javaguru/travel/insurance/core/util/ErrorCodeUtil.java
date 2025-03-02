package org.javaguru.travel.insurance.core.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class ErrorCodeUtil {

    private final Properties errorDescriptions;

    public ErrorCodeUtil() throws IOException {
        errorDescriptions = PropertiesLoaderUtils.loadAllProperties("errorCodes.properties");
    }

    public String getErrorDescription(String errorCode) {
        return errorDescriptions.containsKey(errorCode)
                ? errorDescriptions.getProperty(errorCode)
                : "Unknown error code!";
    }
}
