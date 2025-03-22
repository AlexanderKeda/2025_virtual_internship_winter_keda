package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelElementTest {

    @Mock
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepositoryMock;

    @Mock
    TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldReturnCorrectCountryDefaultDayRate() {
        var medicalRiskLimitLevelElement = new MedicalRiskLimitLevelElement(
                true,
                medicalRiskLimitLevelRepositoryMock
        );
        var expectedLimitLevelCoefficient = new BigDecimal("1.1");
        var medicalRiskLimitLevel = new MedicalRiskLimitLevel(1L,"", expectedLimitLevelCoefficient);
        when(medicalRiskLimitLevelRepositoryMock.findByMedicalRiskLimitIc(requestMock.getMedicalRiskLimitLevel()))
                .thenReturn(Optional.of(medicalRiskLimitLevel));
        assertEquals(expectedLimitLevelCoefficient, medicalRiskLimitLevelElement.calculate(requestMock));
    }

    @Test
    void shouldReturnOneWhenLimitLevelIsDisabled() {
        var medicalRiskLimitLevelElement = new MedicalRiskLimitLevelElement(
                false,
                medicalRiskLimitLevelRepositoryMock
        );
        assertEquals(BigDecimal.ONE, medicalRiskLimitLevelElement.calculate(requestMock));
        Mockito.verifyNoInteractions(requestMock);
        Mockito.verifyNoInteractions(medicalRiskLimitLevelRepositoryMock);
    }

    @Test
    void shouldThrowExceptionWhenDayRateNotFoundInDB() {
        var medicalRiskLimitLevelElement = new MedicalRiskLimitLevelElement(
                true,
                medicalRiskLimitLevelRepositoryMock
        );
        when(medicalRiskLimitLevelRepositoryMock.findByMedicalRiskLimitIc(requestMock.getMedicalRiskLimitLevel()))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> medicalRiskLimitLevelElement.calculate(requestMock));
    }

}