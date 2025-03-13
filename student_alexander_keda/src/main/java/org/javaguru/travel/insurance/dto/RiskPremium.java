package org.javaguru.travel.insurance.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RiskPremium {

    private String riskIC;

    private BigDecimal premium;

}
