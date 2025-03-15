package org.javaguru.travel.insurance.core.underwriting.calculators;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final DateTimeUtil dateTimeUtil;
    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        return BigDecimal.ONE
                .multiply(calculateDayCount(request))
                .multiply(getDefaultDayRate(request));
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

    private BigDecimal calculateDayCount(TravelCalculatePremiumRequest request) {
        return new BigDecimal(dateTimeUtil.calculateDaysBetween(
                request.getAgreementDateFrom(),
                request.getAgreementDateTo()));
    }

    private BigDecimal getDefaultDayRate(TravelCalculatePremiumRequest request) {
        Optional<CountryDefaultDayRate> dayRateOpt = countryDefaultDayRateRepository
                .findByCountryIc(request.getCountry());
        if (dayRateOpt.isEmpty()) {
            throw new RuntimeException("Country day rate not found by countryIC=" + request.getCountry());
        }
        return dayRateOpt.get().getDefaultDayRate();
    }
}
