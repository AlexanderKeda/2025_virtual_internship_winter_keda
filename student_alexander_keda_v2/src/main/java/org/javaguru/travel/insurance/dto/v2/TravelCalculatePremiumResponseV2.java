package org.javaguru.travel.insurance.dto.v2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.dto.CoreResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.util.BigDecimalSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelCalculatePremiumResponseV2 extends CoreResponse {

    private String uuid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate agreementDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate agreementDateTo;

    private String country;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal agreementPremium;

    private List<PersonResponseDTO> persons;

    public TravelCalculatePremiumResponseV2(List<ValidationError> errors) {
        super(errors);
    }
}
