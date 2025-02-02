package org.javaguru.travel.insurance.core;


import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;


class TravelCalculatePremiumServiceImplAIEachFieldTest {
    TravelCalculatePremiumServiceImpl service;
    TravelCalculatePremiumRequest request;

    @BeforeEach
    void setUp() {
        // Создание экземпляра тестируемого класса
        service = new TravelCalculatePremiumServiceImpl();

        // Создание объекта запроса с данными
        request = new TravelCalculatePremiumRequest();
        request.setPersonFirstName("John");
        request.setPersonLastName("Doe");
        request.setAgreementDateFrom(new Date(100000000L));
        request.setAgreementDateTo(new Date(200000000L));
    }

    @Test
    void shouldReturnCorrectFirstName() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля personFirstName
        assertEquals("John", response.getPersonFirstName(), "First name should match");
    }

    @Test
    void shouldReturnCorrectLastName() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля personLastName
        assertEquals("Doe", response.getPersonLastName(), "Last name should match");
    }

    @Test
    void shouldReturnCorrectAgreementDateFrom() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля agreementDateFrom
        assertEquals(new Date(100000000L), response.getAgreementDateFrom(), "Agreement start date should match");
    }

    @Test
    void shouldReturnCorrectAgreementDateTo() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля agreementDateTo
        assertEquals(new Date(200000000L), response.getAgreementDateTo(), "Agreement end date should match");
    }

}
