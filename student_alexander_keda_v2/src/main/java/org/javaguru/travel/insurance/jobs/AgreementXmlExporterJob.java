package org.javaguru.travel.insurance.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(
        name = "agreement.xml.exporter.job.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class AgreementXmlExporterJob {

    @Scheduled(fixedRate = 5000)
    public void doJob() {
        log.info("SimpleJob started");
        log.info("SimpleJob finished");
    }
}
