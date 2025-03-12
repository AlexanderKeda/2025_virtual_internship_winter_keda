package org.javaguru.travel.insurance.core.underwriting;

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
    void shouldReturnCorrectAgreementPrice() {
        BigDecimal premium1 = new BigDecimal("3.38");
        BigDecimal premium2 = new BigDecimal("1.12");
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("RISK_1", "RISK_2"));
        when(calculatorMock1.getRiskIc())
                .thenReturn("RISK_1");
        when(calculatorMock1.calculatePremium(requestMock))
                .thenReturn(premium1);
        when(calculatorMock2.getRiskIc())
                .thenReturn("RISK_2");
        when(calculatorMock2.calculatePremium(requestMock))
                .thenReturn(premium2);
        assertEquals(premium1.add(premium2), underwriting.underwrite(requestMock));
    }

}