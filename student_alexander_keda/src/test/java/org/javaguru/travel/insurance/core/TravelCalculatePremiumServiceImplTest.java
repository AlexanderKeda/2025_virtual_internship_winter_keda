package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TravelCalculatePremiumServiceImplTest {

    private static TravelCalculatePremiumService travelCalculatePremiumService;
    private static DateTimeService dateTimeService;
    private static TravelCalculatePremiumRequest request;
    private static TravelCalculatePremiumResponse response;
    private static LocalDate date1;
    private static LocalDate date2;
    private static final long DAYS = 7;
    private static String firstName;
    private static String lastName;

    @BeforeAll
    static void setUp() {
        dateTimeService = new DateTimeService();
        travelCalculatePremiumService = new TravelCalculatePremiumServiceImpl(dateTimeService);

        date1 = LocalDate.now();
        date2 = date1.plusDays(DAYS);
        firstName = "Ivan";
        lastName = "Ivanov";

        request = new TravelCalculatePremiumRequest(firstName,
                lastName,
                date1,
                date2);

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
        assertEquals(new BigDecimal(DAYS),
                response.getAgreementPrice(),
                "Agreement price is incorrect");
    }

}