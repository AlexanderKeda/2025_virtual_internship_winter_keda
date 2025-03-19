package org.javaguru.travel.insurance.core.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class AgeCalculator {

    public long calculate(LocalDate birthDate) {
        long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        return age >= 0
                ? age
                : -1L;
    }

}
