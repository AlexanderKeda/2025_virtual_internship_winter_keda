package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class MedicalRiskLimitLevelElement implements MedicalRiskElement {

    private final boolean medicalRiskLimitLevelEnabled;
    private final MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    MedicalRiskLimitLevelElement(
            @Value("${medical.risk.limit.level.enabled:false}") boolean medicalRiskLimitLevelEnabled,
            MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository
    ) {
        this.medicalRiskLimitLevelEnabled = medicalRiskLimitLevelEnabled;
        this.medicalRiskLimitLevelRepository = medicalRiskLimitLevelRepository;
    }

    @Override
    public BigDecimal calculate(TravelCalculatePremiumRequestV1 request) {
        return isMedicalRiskLimitLevelEnabled()
                ? getMedicalRiskLimitLevelCoefficient(request)
                : getDefaultCoefficient();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private BigDecimal getMedicalRiskLimitLevelCoefficient(TravelCalculatePremiumRequestV1 request) {
        var medicalRiskLimitLevelOpt = medicalRiskLimitLevelRepository
                .findByMedicalRiskLimitLevelIc(request.getMedicalRiskLimitLevel());
        if (medicalRiskLimitLevelOpt.isEmpty()) {
            throw new RuntimeException("Country day rate not found by countryIC=" + request.getCountry());
        }
        return medicalRiskLimitLevelOpt
                .get()
                .getCoefficient();
    }

    private BigDecimal getDefaultCoefficient() {
        return BigDecimal.ONE;
    }
}
