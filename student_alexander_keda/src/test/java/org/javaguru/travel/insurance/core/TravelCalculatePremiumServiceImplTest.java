package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TravelCalculatePremiumServiceImplTest {

    static TravelCalculatePremiumService travelCalculatePremiumService;

    @BeforeAll
    static void createTravelCalculatePremiumService() {
        travelCalculatePremiumService = new TravelCalculatePremiumServiceImpl();
    }

    @Test
    void shouldResponse() {
        TravelCalculatePremiumRequest request = new TravelCalculatePremiumRequest("Ivanov",
                "Ivan",
                new Date(),
                new Date());

        TravelCalculatePremiumResponse response = travelCalculatePremiumService.calculatePremium(request);

        assertEquals(request.getPersonFirstName(), response.getPersonFirstName());
        assertEquals(request.getPersonLastName(), response.getPersonLastName());
        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom());
        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo());
    }

}