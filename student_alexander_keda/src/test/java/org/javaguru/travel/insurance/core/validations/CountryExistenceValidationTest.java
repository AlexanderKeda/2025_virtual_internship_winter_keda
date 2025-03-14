package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
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

    @InjectMocks
    private CountryExistenceValidation countryExistenceValidation;

    @Mock
    private TravelCalculatePremiumRequest request;

    @Test
    void shouldSucceedWhenCountryExistAndHasRequiredRisks() {
        when(request.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry())
                .thenReturn("LATVIA");
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("COUNTRY", "LATVIA"))
                .thenReturn(Optional.of(new ClassifierValue()));
        assertEquals(Optional.empty(), countryExistenceValidation.validate(request));
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreMissing() {
        when(request.getSelectedRisks())
                .thenReturn(List.of("FAKE_RISK"));
        assertEquals(Optional.empty(), countryExistenceValidation.validate(request));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenRequiredRisksAreNull() {
        when(request.getSelectedRisks())
                .thenReturn(null);
        assertEquals(Optional.empty(), countryExistenceValidation.validate(request));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenCountryIsNullAndHasRequiredRisks() {
        when(request.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry())
                .thenReturn(null);
        assertEquals(Optional.empty(), countryExistenceValidation.validate(request));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldSucceedWhenCountryIsEmptyAndHasRequiredRisks() {
        when(request.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry())
                .thenReturn("");
        assertEquals(Optional.empty(), countryExistenceValidation.validate(request));
        Mockito.verifyNoInteractions(classifierValueRepositoryMock);
    }

    @Test
    void shouldReturnErrorWhenCountryIsNotExistAndHasRequiredRisks() {
        String countryName = "FAKE_COUNTRY";
        Placeholder correctPlaceholder = new Placeholder("NOT_EXISTING_COUNTRY", countryName);
        when(request.getSelectedRisks())
                .thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry())
                .thenReturn(countryName);
        when(classifierValueRepositoryMock.findByClassifierTitleAndIc("COUNTRY", countryName))
                .thenReturn(Optional.empty());
        when(errorFactoryMock.buildError(
                "ERROR_CODE_11",
                List.of(correctPlaceholder)
        )).thenReturn(new ValidationError("", ""));
        Optional<ValidationError> errorOpt = countryExistenceValidation.validate(request);
        assertTrue(errorOpt.isPresent());
        assertEquals(new ValidationError("", ""), errorOpt.get());
    }

}