package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
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
    private DateTimeService dateTimeServiceMock;
    @Mock
    private TravelCalculatePremiumRequestValidator requestValidatorMock;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl travelCalculatePremiumService;

    private TravelCalculatePremiumRequest request;
    private TravelCalculatePremiumResponse response;

    private long days = 7;
    private String firstName = "FirstName";
    private String lastName = "LastName";
    private LocalDate date1 = LocalDate.now();
    private LocalDate date2 = date1.plusDays(days);

    @Test
    void shouldResponseFirstName() {
        request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(dateTimeServiceMock.calculateDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(days);
        response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getPersonFirstName(),
                response.getPersonFirstName(),
                "First name should match");
    }

    @Test
    void shouldResponseLastName() {
        request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(dateTimeServiceMock.calculateDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(days);
        response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getPersonLastName(),
                response.getPersonLastName(),
                "Last name should match");
    }

    @Test
    void shouldResponseDateFrom() {
        request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(dateTimeServiceMock.calculateDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(days);
        response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getAgreementDateFrom(),
                response.getAgreementDateFrom(),
                "Agreement start date should match");
    }

    @Test
    void shouldResponseDateTo() {
        request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(dateTimeServiceMock.calculateDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(days);
        response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(request.getAgreementDateTo(),
                response.getAgreementDateTo(),
                "Agreement end date should match");
    }

    @Test
    void shouldResponseCorrectAgreementPrice() {
        request = createValidRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of());
        when(dateTimeServiceMock.calculateDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(days);
        response = travelCalculatePremiumService.calculatePremium(request);
        assertEquals(new BigDecimal(days),
                response.getAgreementPrice(),
                "Agreement price is incorrect");
    }

    @Test
    void shouldReturnResponseWithErrors() {
        request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        response = travelCalculatePremiumService.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    void allFieldsMustBeEmptyWhenResponseContainsError() {
        request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        response = travelCalculatePremiumService.calculatePremium(request);
        assertNull(response.getPersonFirstName());
        assertNull(response.getPersonLastName());
        assertNull(response.getAgreementDateFrom());
        assertNull(response.getAgreementDateTo());
        assertNull(response.getAgreementPrice());
    }

    @Test
    void shouldNotBeInteractionWithDateTimeServiceWhenResponseContainsError() {
        request = new TravelCalculatePremiumRequest();
        when(requestValidatorMock.validate(request))
                .thenReturn(List.of(new ValidationError("field", "message")));
        response = travelCalculatePremiumService.calculatePremium(request);
        Mockito.verifyNoInteractions(dateTimeServiceMock);
    }

    TravelCalculatePremiumRequest createValidRequest() {
        return request = TravelCalculatePremiumRequest.builder()
                .personFirstName(firstName)
                .personLastName(lastName)
                .agreementDateFrom(date1)
                .agreementDateTo(date2)
                .build();
    }
}