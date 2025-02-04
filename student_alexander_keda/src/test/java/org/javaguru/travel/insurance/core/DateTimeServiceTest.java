package org.javaguru.travel.insurance.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {

    private DateTimeService dateTimeService;
    private long days = 7;
    private LocalDate date1;
    private LocalDate date2;

    @BeforeEach
    void setUp() {
        dateTimeService = new DateTimeService();
        date1 = LocalDate.now();
        date2 = date1.plusDays(days);
    }

    @Test
    void shouldDaysBetweenBePositive() {
        assertEquals(days,
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
        assertEquals(-days,
                dateTimeService.calculateDaysBetween(date2, date1),
                "Incorrect value of days between dates");
    }
}