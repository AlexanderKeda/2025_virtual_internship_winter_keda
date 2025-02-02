package org.javaguru.travel.insurance.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TravelCalculatePremiumResponseTest {
    static TravelCalculatePremiumResponse response;

    @BeforeAll
    static void createTravelCalculatePremiumResponse() {
        response = new TravelCalculatePremiumResponse("Ivanov", "Ivan",
                LocalDate.of(2024, 6, 20),
                LocalDate.of(2024, 6, 25));
    }

    @Test
    void shouldCalculateAgreementPriceCorrect() {
        assertEquals(new BigDecimal("5"), response.getAgreementPrice(), "Agreement price is incorrect");
    }
}