package org.javaguru.travel.insurance.core;


import org.javaguru.travel.insurance.rest.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.rest.TravelCalculatePremiumResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


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
        request.setAgreementDateFrom(LocalDate.of(2024, 6, 20));
        request.setAgreementDateTo(LocalDate.of(2024, 6, 25));
    }

    @Test
    void shouldReturnCorrectFirstName() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля personFirstName
        assertEquals(request.getPersonFirstName(), response.getPersonFirstName(), "First name should match");
    }

    @Test
    void shouldReturnCorrectLastName() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля personLastName
        assertEquals(request.getPersonLastName(), response.getPersonLastName(), "Last name should match");
    }

    @Test
    void shouldReturnCorrectAgreementDateFrom() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля agreementDateFrom
        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom(), "Agreement start date should match");
    }

    @Test
    void shouldReturnCorrectAgreementDateTo() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка поля agreementDateTo
        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo(), "Agreement end date should match");
    }

}
