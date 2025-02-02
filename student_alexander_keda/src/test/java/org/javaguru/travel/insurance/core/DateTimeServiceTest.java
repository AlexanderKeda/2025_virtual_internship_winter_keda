package org.javaguru.travel.insurance.core;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {
    static DateTimeService dateTimeService;

    @Test
    void shouldDaysBetweenBePositive() {
        assertEquals(10L,
                DateTimeService.calculateDaysBetween(LocalDate.of(2025, 1, 10),
                        LocalDate.of(2025, 1, 20)),
                "Incorrect value of days between dates");
    }

    @Test
    void shouldDaysBetweenBeZero() {
        assertEquals(0L,
                DateTimeService.calculateDaysBetween(LocalDate.of(2025, 1, 10),
                        LocalDate.of(2025, 1, 10)),
                "Incorrect value of days between dates");
    }

    @Test
    void shouldDaysBetweenBeNegative() {
        assertEquals(-10L,
                DateTimeService.calculateDaysBetween(LocalDate.of(2025, 1, 20),
                        LocalDate.of(2025, 1, 10)),
                "Incorrect value of days between dates");
    }
}