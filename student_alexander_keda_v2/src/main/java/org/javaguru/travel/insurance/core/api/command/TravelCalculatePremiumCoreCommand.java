package org.javaguru.travel.insurance.core.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;

@Getter
@AllArgsConstructor
public class TravelCalculatePremiumCoreCommand {

    private AgreementDTO agreement;

}
