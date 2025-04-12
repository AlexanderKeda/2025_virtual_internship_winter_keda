package org.javaguru.travel.insurance.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorDTO {

    private String errorCode;
    private String description;

}
