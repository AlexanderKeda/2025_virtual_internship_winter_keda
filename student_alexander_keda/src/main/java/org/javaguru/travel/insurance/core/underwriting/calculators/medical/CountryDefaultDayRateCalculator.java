package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class CountryDefaultDayRateCalculator implements MedicalRiskElementCalculator {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public BigDecimal calculate(TravelCalculatePremiumRequest request) {
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
