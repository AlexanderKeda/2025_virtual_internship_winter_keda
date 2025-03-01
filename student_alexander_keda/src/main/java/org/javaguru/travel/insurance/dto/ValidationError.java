package org.javaguru.travel.insurance.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

    private String errorCode;
    private String description;
}
