package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private TravelPremiumUnderwriting underwritingMock;
    @Mock
    private TravelCalculatePremiumRequestValidator requestValidatorMock;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl travelCalculatePremiumService;

    private static final long DAYS = 7;
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final LocalDate DATE_1 = LocalDate.now();
    private static final LocalDate DATE_2 = DATE_1.plusDays(DAYS);

    @Test
    void shouldResponseFirstName() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getPersonFirstName(),
                response.getPersonFirstName(),
                "First name should match");
    }

    @Test
    void shouldResponseLastName() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getPersonLastName(),
                response.getPersonLastName(),
                "Last name should match");
    }

    @Test
    void shouldResponseDateFrom() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getAgreementDateFrom(),
                response.getAgreementDateFrom(),
                "Agreement start date should match");
    }

    @Test
    void shouldResponseDateTo() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getAgreementDateTo(),
                response.getAgreementDateTo(),
                "Agreement end date should match");
    }

    @Test
    void shouldResponseCorrectAgreementPrice() {
        var request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(underwritingMock.underwrite(request))
                .thenReturn(new BigDecimal(DAYS));
        var response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(new BigDecimal(DAYS),
                response.getAgreementPrice(),
                "Agreement price is incorrect");
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
        assertNull(response.getAgreementPrice());
    }

    @Test
    void shouldNotBeInteractionWithDateTimeServiceWhenResponseContainsError() {
        var request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        travelCalculatePremiumService.calculatePremium(request);
        Mockito.verifyNoInteractions(underwritingMock);
    }

    TravelCalculatePremiumRequest createValidRequest() {
        return TravelCalculatePremiumRequest.builder()
                .personFirstName(FIRST_NAME)
                .personLastName(LAST_NAME)
                .agreementDateFrom(DATE_1)
                .agreementDateTo(DATE_2)
                .build();
    }
}