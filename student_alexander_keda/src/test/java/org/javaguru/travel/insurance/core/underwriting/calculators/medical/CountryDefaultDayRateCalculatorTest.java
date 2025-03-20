package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
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
class CountryDefaultDayRateCalculatorTest {

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepositoryMock;

    @InjectMocks
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectCountryDefaultDayRate() {
        BigDecimal expectedDayRate = new BigDecimal("1.1");
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(requestMock.getCountry()))
                .thenReturn(Optional.of(new CountryDefaultDayRate(1L, "", expectedDayRate)));
        assertEquals(expectedDayRate, countryDefaultDayRateCalculator.calculate(requestMock));
    }

    @Test
    void shouldThrowExceptionWhenDayRateNotFoundInDB() {
        when(countryDefaultDayRateRepositoryMock.findByCountryIc(requestMock.getCountry()))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> countryDefaultDayRateCalculator.calculate(requestMock));
    }

}