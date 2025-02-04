package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private DateTimeService dateTimeServiceMock;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl travelCalculatePremiumService;

    private TravelCalculatePremiumRequest request;
    private TravelCalculatePremiumResponse response;

    private long days = 7;
    private LocalDate date1;
    private LocalDate date2;
    private String firstName;
    private String lastName;

    @BeforeEach
    void setUp() {
        date1 = LocalDate.now();
        date2 = date1.plusDays(days);
        firstName = "FirstName";
        lastName = "LastName";

        request = new TravelCalculatePremiumRequest(firstName,
                lastName,
                date1,
                date2);

        Mockito.when(dateTimeServiceMock.calculateDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(days);

        response = travelCalculatePremiumService.calculatePremium(request);
    }

    @Test
    void shouldResponseFirstName() {
        assertEquals(request.getPersonFirstName(),
                response.getPersonFirstName(),
                "First name should match");
    }

    @Test
    void shouldResponseLastName() {
        assertEquals(request.getPersonLastName(),
                response.getPersonLastName(),
                "Last name should match");
    }

    @Test
    void shouldResponseDateFrom() {
        assertEquals(request.getAgreementDateFrom(),
                response.getAgreementDateFrom(),
                "Agreement start date should match");
    }

    @Test
    void shouldResponseDateTo() {
        assertEquals(request.getAgreementDateTo(),
                response.getAgreementDateTo(),
                "Agreement end date should match");
    }

    @Test
    void shouldResponseCorrectAgreementPrice() {
        assertEquals(new BigDecimal(days),
                response.getAgreementPrice(),
                "Agreement price is incorrect");
    }

}