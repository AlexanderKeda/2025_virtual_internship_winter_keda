package org.javaguru.travel.insurance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsuranceApplication {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceApplication.class);

    public static void main(String[] args) {
        logger.info("Запуск приложения");
        SpringApplication.run(InsuranceApplication.class, args);
    }

}
