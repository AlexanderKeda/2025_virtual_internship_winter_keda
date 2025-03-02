package org.javaguru.travel.insurance.core;

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
class TravelPremiumUnderwritingTest {

    @Mock
    private DateTimeUtil dateTimeUtilMock;

    @InjectMocks
    private TravelPremiumUnderwriting underwriting;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    private static final long DAYS = 7;

    @Test
    void shouldResponseCorrectAgreementPrice() {
        when(dateTimeUtilMock.calculateDaysBetween(requestMock.getAgreementDateFrom(), requestMock.getAgreementDateTo()))
                .thenReturn(DAYS);
        assertEquals(new BigDecimal(DAYS), underwriting.underwrite(requestMock));
    }

}