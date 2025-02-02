package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.rest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

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
        request.setAgreementDateFrom(new Date(100000000L));
        request.setAgreementDateTo(new Date(200000000L));
    }

    @Test
    void shouldReturnCorrectResponse() {
        // Вызов метода calculatePremium
        TravelCalculatePremiumResponse response = service.calculatePremium(request);

        // Проверка полей возвращаемого ответа
        assertEquals("John", response.getPersonFirstName(), "First name should match");
        assertEquals("Doe", response.getPersonLastName(), "Last name should match");
        assertEquals(new Date(100000000L), response.getAgreementDateFrom(), "Agreement start date should match");
        assertEquals(new Date(200000000L), response.getAgreementDateTo(), "Agreement end date should match");
    }


}
