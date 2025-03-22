package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.MODULE)
class AgeCoefficientElement implements MedicalRiskElement {

    private final AgeCalculator ageCalculator;
    private final AgeCoefficientRepository ageCoefficientRepository;

    @Override
    public BigDecimal calculate(TravelCalculatePremiumRequest request) {
        var ageCoefficientOpt = ageCoefficientRepository
                .findByAge(ageCalculator.calculate(request.getPersonBirthDate()));
        if (ageCoefficientOpt.isEmpty()) {
            throw new RuntimeException("Age coefficient not found by birth date=" + request.getPersonBirthDate());
        }
        return ageCoefficientOpt
                .get()
                .getCoefficient();
    }
}
