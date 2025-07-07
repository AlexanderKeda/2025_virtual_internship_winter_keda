package org.javaguru.travel.insurance.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobExample {

    @Scheduled(fixedRate = 5000)
    public void doJob() {
        log.info("SimpleJob started");
        log.info("SimpleJob finished");
    }
}
