package org.javaguru.travel.insurance.core;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {

    private final DateTimeService dateTimeService = new DateTimeService();
    private static final long DAYS = 7;
    private static final LocalDate DATE_1 = LocalDate.now();
    private static final LocalDate DATE_2 = DATE_1.plusDays(DAYS);

    @Test
    void shouldDaysBetweenBePositive() {
        assertEquals(DAYS,
                dateTimeService.calculateDaysBetween(DATE_1, DATE_2),
                "Incorrect value of days between dates");
    }

    @Test
    void shouldDaysBetweenBeZero() {
        assertEquals(0L,
                dateTimeService.calculateDaysBetween(DATE_1, DATE_1),
                "Incorrect value of days between dates");
    }

    @Test
    void shouldDaysBetweenBeNegative() {
        assertEquals(-DAYS,
                dateTimeService.calculateDaysBetween(DATE_2, DATE_1),
                "Incorrect value of days between dates");
    }
}