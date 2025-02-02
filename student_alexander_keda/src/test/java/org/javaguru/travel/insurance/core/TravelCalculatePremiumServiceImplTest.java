package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TravelCalculatePremiumServiceImplTest {

    static TravelCalculatePremiumService travelCalculatePremiumService;
    static TravelCalculatePremiumRequest request;
    static TravelCalculatePremiumResponse response;

    @BeforeAll
    static void createTravelCalculatePremiumService() {
        travelCalculatePremiumService = new TravelCalculatePremiumServiceImpl();

        request = new TravelCalculatePremiumRequest("Ivanov",
                "Ivan",
                LocalDate.of(2024, 6, 20),
                LocalDate.of(2024, 6, 25));

        response = travelCalculatePremiumService.calculatePremium(request);
    }

    @Test
    void shouldResponseFirstName() {
        assertEquals(request.getPersonFirstName(), response.getPersonFirstName(), "First name should match");
    }

    @Test
    void shouldResponseLastName() {
        assertEquals(request.getPersonLastName(), response.getPersonLastName(), "Last name should match");
    }

    @Test
    void shouldResponseDateFrom() {
        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom(), "Agreement start date should match");
    }

    @Test
    void shouldResponseDateTo() {
        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo(), "Agreement end date should match");
    }

    @Test
    void shouldReturnCorrectAgreementPrice() {
        assertEquals(new BigDecimal("5"),response.getAgreementPrice(), "Agreement price is incorrect");
    }

}