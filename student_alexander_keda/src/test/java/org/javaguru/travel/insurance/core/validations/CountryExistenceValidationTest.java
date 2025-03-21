package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryExistenceValidationTest {

    @Mock
    private ValidationErrorFactory errorFactoryMock;

    @Mock
    private ClassifierValueRepository classifierValueRepositoryMock;

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @InjectMocks
    private CountryExistenceValidation countryExistenceValidation;

    @Mock
    private TravelCalculatePremiumRequest requestMock;

    @Test
    void shouldSucceedWhenCountryExistAndHasRequiredRisks() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn("LATVIA");
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("COUNTRY", "LATVIA"))
                .thenReturn(Optional.of(new ClassifierValue()));
        when(countryDefaultDayRateRepository.findByCountryIc("LATVIA"))
                .thenReturn(Optional.of(new CountryDefaultDayRate()));
        assertEquals(Optional.empty(), countryExistenceValidation.validate(requestMock));
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreMissing() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("FAKE_RISK"));
        assertEquals(Optional.empty(), countryExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreNull() {
        when(requestMock.getSelectedRisks())
                .thenReturn(null);
        assertEquals(Optional.empty(), countryExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenCountryIsNullAndHasRequiredRisks() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn(null);
        assertEquals(Optional.empty(), countryExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenCountryIsEmptyAndHasRequiredRisks() {
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn("");
        assertEquals(Optional.empty(), countryExistenceValidation.validate(requestMock));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsNotExistAndHasRequiredRisks() {
        String countryName = "FAKE_COUNTRY";
        Placeholder correctPlaceholder = new Placeholder("NOT_EXISTING_COUNTRY", countryName);
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn(countryName);
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("COUNTRY", countryName))
                .thenReturn(Optional.empty());
        when(errorFactoryMock.buildError(
                "ERROR_CODE_11",
                List.of(correctPlaceholder)
        )).thenReturn(new ValidationError("", ""));
        var errorOpt = countryExistenceValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

    @Test
    void shouldReturnErrorWhenDefaultDayRateIsNotExistAndHasRequiredRisks() {
        String countryName = "FAKE_COUNTRY";
        Placeholder correctPlaceholder = new Placeholder("NOT_EXISTING_COUNTRY", countryName);
        when(requestMock.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(requestMock.getCountry())
                .thenReturn(countryName);
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("COUNTRY", countryName))
                .thenReturn(Optional.of(new ClassifierValue()));
        when(countryDefaultDayRateRepository.findByCountryIc(countryName))
                .thenReturn(Optional.empty());
        when(errorFactoryMock.buildError(
                "ERROR_CODE_11",
                List.of(correctPlaceholder)
        )).thenReturn(new ValidationError("", ""));
        var errorOpt = countryExistenceValidation.validate(requestMock);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

}