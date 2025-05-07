package org.javaguru.travel.insurance.rest.v1;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TravelCalculatePremiumRequestExecutionTimeLogger {
    private static final Logger logger = LoggerFactory.getLogger(TravelCalculatePremiumRequestExecutionTimeLogger.class);

    void log(Stopwatch stopwatch) {
        stopwatch.stop();
        logger.info("Request processing time (µs): {}", stopwatch.elapsed(TimeUnit.MICROSECONDS));
    }
}
