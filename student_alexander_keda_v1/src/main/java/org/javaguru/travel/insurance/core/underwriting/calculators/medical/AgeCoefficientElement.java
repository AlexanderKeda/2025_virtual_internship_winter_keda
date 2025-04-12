package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class AgeCoefficientElement implements MedicalRiskElement {

    private final boolean medicalRiskAgeCoefficientEnabled;
    private final AgeCalculator ageCalculator;
    private final AgeCoefficientRepository ageCoefficientRepository;

    AgeCoefficientElement(
            @Value("${medical.risk.age.coefficient.enabled:false}") boolean medicalRiskAgeCoefficientEnabled,
            AgeCalculator ageCalculator,
            AgeCoefficientRepository ageCoefficientRepository
    ) {
        this.medicalRiskAgeCoefficientEnabled = medicalRiskAgeCoefficientEnabled;
        this.ageCalculator = ageCalculator;
        this.ageCoefficientRepository = ageCoefficientRepository;
    }

    @Override
    public BigDecimal calculate(TravelCalculatePremiumRequestV1 request) {
        return isMedicalRiskAgeCoefficientEnabled()
                ? getAgeCoefficient(request)
                : getDefaultCoefficient();
    }

    private BigDecimal getAgeCoefficient(TravelCalculatePremiumRequestV1 request) {
        var ageCoefficientOpt = ageCoefficientRepository
                .findByAge(ageCalculator.calculate(request.getPersonBirthDate()));
        if (ageCoefficientOpt.isEmpty()) {
            throw new RuntimeException("Age coefficient not found by birth date=" + request.getPersonBirthDate());
        }
        return ageCoefficientOpt
                .get()
                .getCoefficient();
    }

    private boolean isMedicalRiskAgeCoefficientEnabled() {
        return medicalRiskAgeCoefficientEnabled;
    }

    private BigDecimal getDefaultCoefficient() {
        return BigDecimal.ONE;
    }
}
