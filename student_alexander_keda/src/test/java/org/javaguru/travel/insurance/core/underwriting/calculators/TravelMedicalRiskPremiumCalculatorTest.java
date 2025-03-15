package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskPremiumCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtilMock;

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepositoryMock;

    @InjectMocks
    private TravelMedicalRiskPremiumCalculator medicalRiskPremiumCalculator;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectPremiumPrice() {
        long days = 7L;
        long dayRate = 2L;
        Optional<CountryDefaultDayRate> dayRateOpt = Optional.of(new CountryDefaultDayRate(
                1L,
                "Country",
                BigDecimal.valueOf(dayRate)
        ));
        when(dateTimeUtilMock.calculateDaysBetween(requestMock.getAgreementDateFrom(), requestMock.getAgreementDateTo()))
                .thenReturn(days);
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(requestMock.getCountry()))
                .thenReturn(dayRateOpt);
        assertEquals(new BigDecimal(days * dayRate), medicalRiskPremiumCalculator.calculatePremium(requestMock));
    }

    @Test
    void shouldThrowRuntimeExceptionWhenDayRateDoesNotExist() {
        long days = 7L;
        when(dateTimeUtilMock.calculateDaysBetween(requestMock.getAgreementDateFrom(), requestMock.getAgreementDateTo()))
                .thenReturn(days);
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(requestMock.getCountry()))
                .thenReturn(Optional.empty());
        assertThrows(
                RuntimeException.class,
                () -> medicalRiskPremiumCalculator.calculatePremium(requestMock)
                );
    }

}