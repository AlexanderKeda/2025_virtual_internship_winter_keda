package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private TravelPremiumUnderwriting underwritingMock;
    @Mock
    private TravelCalculatePremiumRequestValidator requestValidatorMock;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl travelCalculatePremiumService;

    @Mock
    private TravelPremiumCalculationResult calculationResultMock;

    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final LocalDate BIRTH_DATE = LocalDate.now().minusYears(20);
    private static final LocalDate DATE_1 = LocalDate.now();
    private static final LocalDate DATE_2 = DATE_1.plusDays(7);
    private static final String RISK = "risk1";
    private static final String COUNTRY = "LATVIA";

    @Test
    void shouldReturnFirstName() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getPersonFirstName(),
                response.getPersonFirstName(),
                "First name should match");
    }

    @Test
    void shouldReturnLastName() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getPersonLastName(),
                response.getPersonLastName(),
                "Last name should match");
    }

    @Test
    void shouldReturnDateFrom() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getAgreementDateFrom(),
                response.getAgreementDateFrom(),
                "Agreement start date should match");
    }

    @Test
    void shouldReturnDateTo() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getAgreementDateTo(),
                response.getAgreementDateTo(),
                "Agreement end date should match");
    }

    @Test
    void shouldReturnCorrectTotalPremium() {
        var request = createValidRequest();
        var totalPremium = new BigDecimal("3.33");
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        when(calculationResultMock.getTotalPremium())
                .thenReturn(totalPremium);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(totalPremium,
                response.getAgreementPremium(),
                "Agreement price is incorrect");
    }

    @Test
    void shouldReturnCorrectRiskPremiumList() {
        var request = createValidRequest();
        var riskPremiums = List.of(
                new RiskPremium(null, null),
                new RiskPremium(null, null)
        );
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        when(calculationResultMock.getRiskPremiums())
                .thenReturn(riskPremiums);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(riskPremiums,
                response.getRisks(),
                "Agreement risks is incorrect");
    }

    @Test
    void shouldReturnCountry() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.calculatePremium(request))
                .thenReturn(calculationResultMock);
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getCountry(),
                response.getCountry(),
                "Country should match");
    }

    @Test
    void shouldReturnResponseWithErrors() {
        var request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    void allFieldsMustBeEmptyWhenResponseContainsError() {
        var request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertNull(response.getPersonFirstName());
        assertNull(response.getPersonLastName());
        assertNull(response.getAgreementDateFrom());
        assertNull(response.getAgreementDateTo());
        assertNull(response.getAgreementPremium());
        assertNull(response.getRisks());
    }

    @Test
    void shouldNotBeInteractionWithUnderwritingWhenResponseContainsError() {
        var request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        travelCalculatePremiumService.calculatePremium(request);
        Mockito.verifyNoInteractions(underwritingMock);
    }

    TravelCalculatePremiumRequest createValidRequest() {
        return new TravelCalculatePremiumRequest(
                FIRST_NAME,
                LAST_NAME,
                BIRTH_DATE,
                DATE_1,
                DATE_2,
                COUNTRY,
                List.of(RISK)
        );
    }
}