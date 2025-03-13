package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPremiumUnderwritingImpTest {

    @Mock
    private SelectedRisksPremiumCalculator risksPremiumCalculatorMock;

    @InjectMocks
    private TravelPremiumUnderwritingImp underwriting;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectCalculationResult() {
        var riskPremium1 = new RiskPremium(
                "RISK_1",
                new BigDecimal("3.38")
        );
        var riskPremium2 = new RiskPremium(
                "RISK_2",
                new BigDecimal("1.12")
        );
        var expectedResult = new TravelPremiumCalculationResult(
                riskPremium1.premium().add(riskPremium2.premium()),
                List.of(riskPremium1, riskPremium2)
        );

        when(risksPremiumCalculatorMock.calculatePremiumForAllRisks(requestMock))
                .thenReturn(List.of(riskPremium1, riskPremium2));

        assertEquals(expectedResult, underwriting.calculatePremium(requestMock));
    }

}