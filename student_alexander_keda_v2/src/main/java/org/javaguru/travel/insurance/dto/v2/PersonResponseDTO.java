package org.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.util.BigDecimalSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {

    private String personFirstName;
    private String personLastName;

    private String personCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate personBirthDate;

    private String medicalRiskLimitLevel;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal personPremium;

    @JsonAlias("person_risks")
    private List<RiskPremium> personRisks;

}
