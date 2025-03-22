package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

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
class DayCountElementTest {

    @Mock
    private DateTimeUtil dateTimeUtilMock;

    @InjectMocks
    private DayCountElement dayCountElement;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectDayCount() {
        long dayCount = 10;
        when(dateTimeUtilMock.calculateDaysBetween(requestMock.getAgreementDateFrom(),
                requestMock.getAgreementDateTo()))
                .thenReturn(dayCount);
        assertEquals(new BigDecimal(dayCount), dayCountElement.calculate(requestMock));
    }

}