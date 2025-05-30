package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class CountryDefaultDayRateElement implements MedicalRiskElement {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public BigDecimal calculate(TravelCalculatePremiumRequestV1 request) {
        var dayRateOpt = countryDefaultDayRateRepository
                .findByCountryIc(request.getCountry());
        if (dayRateOpt.isEmpty()) {
            throw new RuntimeException("Country day rate not found by countryIC=" + request.getCountry());
        }
        return dayRateOpt
                .get()
                .getDefaultDayRate();
    }
}
