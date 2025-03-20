package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.AgeCalculator;
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
class AgeCoefficientCalculatorTest {

    @Mock
    private AgeCalculator ageCalculatorMock;

    @Mock
    private AgeCoefficientRepository ageCoefficientRepositoryMock;

    @InjectMocks
    private AgeCoefficientCalculator ageCoefficientCalculator;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectAgeCoefficient() {
        BigDecimal expectedCoefficient = new BigDecimal("1.3");
        when(ageCalculatorMock.calculate(requestMock.getPersonBirthDate()))
                .thenReturn(25);
        when(ageCoefficientRepositoryMock.findByAge(25))
                .thenReturn(Optional.of(new AgeCoefficient(1L,20,40, expectedCoefficient)));
        assertEquals(expectedCoefficient, ageCoefficientCalculator.calculate(requestMock));
    }

    @Test
    void shouldThrowExceptionWhenCoefficientNotFoundInDB() {
        when(ageCalculatorMock.calculate(requestMock.getPersonBirthDate()))
                .thenReturn(25);
        when(ageCoefficientRepositoryMock.findByAge(25))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> ageCoefficientCalculator.calculate(requestMock));
    }

}