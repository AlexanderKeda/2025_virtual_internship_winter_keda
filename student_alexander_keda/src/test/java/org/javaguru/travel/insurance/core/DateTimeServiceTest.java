package org.javaguru.travel.insurance.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {

    private static final DateTimeService dateTimeService = new DateTimeService();
    private static final long DAYS = 7;
    private static LocalDate date1;
    private static LocalDate date2;

    @BeforeAll
    static void setUp() {
        date1 = LocalDate.now();
        date2 = date1.plusDays(DAYS);
    }

    @Test
    void shouldDaysBetweenBePositive() {
        assertEquals(DAYS,
                dateTimeService.calculateDaysBetween(date1, date2),
                "Incorrect value of days between dates");
    }

    @Test
    void shouldDaysBetweenBeZero() {
        assertEquals(0L,
                dateTimeService.calculateDaysBetween(date1, date1),
                "Incorrect value of days between dates");
    }

    @Test
    void shouldDaysBetweenBeNegative() {
        assertEquals(-DAYS,
                dateTimeService.calculateDaysBetween(date2, date1),
                "Incorrect value of days between dates");
    }
}