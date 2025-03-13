package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.underwriting.calculators.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPremiumUnderwritingImpTest {

    @Mock
    private TravelRiskPremiumCalculator calculatorMock1;

    @Mock
    private TravelRiskPremiumCalculator calculatorMock2;

    private TravelPremiumUnderwritingImp underwriting;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @BeforeEach
    void setUp() {
        underwriting = new TravelPremiumUnderwritingImp(List.of(calculatorMock1, calculatorMock2));
    }

    @Test
    void shouldReturnCorrectCalculationResult() {
        var riskIc1 = "RISK_1";
        var premium1 = new BigDecimal("3.38");
        var riskIc2 = "RISK_2";
        var premium2 = new BigDecimal("1.12");

        var riskPremium1 = new RiskPremium(
                riskIc1,
                premium1
        );
        var riskPremium2 = new RiskPremium(
                riskIc2,
                premium2
        );
        var expectedResult = new TravelPremiumCalculationResult(
                premium1.add(premium2),
                List.of(riskPremium1, riskPremium2)
        );

        when(requestMock.getSelectedRisks())
                .thenReturn(List.of(riskIc1, riskIc2));
        when(calculatorMock1.getRiskIc())
                .thenReturn(riskIc1);
        when(calculatorMock1.calculatePremium(requestMock))
                .thenReturn(premium1);
        when(calculatorMock2.getRiskIc())
                .thenReturn(riskIc2);
        when(calculatorMock2.calculatePremium(requestMock))
                .thenReturn(premium2);

        assertEquals(expectedResult, underwriting.calculatePremium(requestMock));
    }

}