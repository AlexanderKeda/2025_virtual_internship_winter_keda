package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
                new Date(10000000L),
                new Date(20000000L));

        response = travelCalculatePremiumService.calculatePremium(request);
    }

    @Test
    void shouldResponseFirstName() {
        assertEquals(request.getPersonFirstName(), response.getPersonFirstName());
    }

    @Test
    void shouldResponseLastName() {
        assertEquals(request.getPersonLastName(), response.getPersonLastName());
    }

    @Test
    void shouldResponseDateFrom() {
        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom());
    }

    @Test
    void shouldResponseDateTo() {
        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo());
    }

}