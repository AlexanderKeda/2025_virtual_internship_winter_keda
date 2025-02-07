package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPremiumUnderwritingTest {

    @Mock
    private DateTimeService dateTimeServiceMock;

    @InjectMocks
    private TravelPremiumUnderwriting underwriting;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    private final long days = 7;
    private final LocalDate date1 = LocalDate.now();
    private final LocalDate date2 = date1.plusDays(days);

    @Test
    void shouldResponseCorrectAgreementPrice() {
        when(requestMock.getAgreementDateFrom()).thenReturn(date1);
        when(requestMock.getAgreementDateTo()).thenReturn(date2);
        when(dateTimeServiceMock.calculateDaysBetween(requestMock.getAgreementDateFrom(), requestMock.getAgreementDateTo()))
                .thenReturn(days);
        assertEquals(new BigDecimal(days), underwriting.underwrite(requestMock));
    }

}