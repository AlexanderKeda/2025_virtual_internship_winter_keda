package org.javaguru.travel.insurance.core;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class DateTimeService {
    public long calculateDaysBetween(LocalDate dateFrom, LocalDate dateTo) {
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }
}
