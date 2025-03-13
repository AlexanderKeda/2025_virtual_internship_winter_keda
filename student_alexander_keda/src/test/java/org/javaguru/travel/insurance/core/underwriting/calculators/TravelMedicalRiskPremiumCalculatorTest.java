package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskPremiumCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtilMock;

    @InjectMocks
    private TravelMedicalRiskPremiumCalculator medicalRiskPremiumCalculator;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectPremiumPrice() {
        long days = 7L;
        when(dateTimeUtilMock.calculateDaysBetween(requestMock.getAgreementDateFrom(), requestMock.getAgreementDateTo()))
                .thenReturn(days);
        assertEquals(new BigDecimal(days), medicalRiskPremiumCalculator.calculatePremium(requestMock));
    }

}