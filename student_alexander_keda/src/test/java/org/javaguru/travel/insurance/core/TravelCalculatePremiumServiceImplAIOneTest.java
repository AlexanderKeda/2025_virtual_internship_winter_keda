package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class TravelCalculatePremiumServiceImplAIOneTest {

    TravelCalculatePremiumServiceImpl service;
    TravelCalculatePremiumRequest request;

    @BeforeEach
    void setUp() {
        // Создаем экземпляр тестируемого класса
        service = new TravelCalculatePremiumServiceImpl();

        // Создаем реальный объект запроса с данными
        request = new TravelCalculatePremiumRequest();
        request.setPersonFirstName("John");
        request.setPersonLastName("Doe");
        request.setAgreementDateFrom(LocalDate.of(2024, 6, 20));
        request.setAgreementDateTo(LocalDate.of(2024, 6, 25));
    }

    @Test
    void shouldReturnCorrectResponse() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка полей возвращаемого ответа
        assertEquals(request.getPersonFirstName(), response.getPersonFirstName(), "First name should match");
        assertEquals(request.getPersonLastName(), response.getPersonLastName(), "Last name should match");
        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom(), "Agreement start date should match");
        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo(), "Agreement end date should match");
    }


}
