package org.javaguru.travel.insurance.core.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RiskDTO {

    private String riskIc;
    private BigDecimal premium;

}
