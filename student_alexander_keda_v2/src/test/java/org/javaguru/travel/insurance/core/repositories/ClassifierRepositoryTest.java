package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.Classifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassifierRepositoryTest {

    @Autowired private ClassifierRepository classifierRepository;

    @Test
    void shouldFindRiskTypeClassifier() {
        Optional<Classifier> riskTypeOpt = classifierRepository.findByTitle("RISK_TYPE");
        assertTrue(riskTypeOpt.isPresent());
        assertEquals("RISK_TYPE", riskTypeOpt.get().getTitle());
    }

    @Test
    void shouldNotFindFakeClassifier() {
        Optional<Classifier> riskTypeOpt = classifierRepository.findByTitle("FAKE");
        assertTrue(riskTypeOpt.isEmpty());
    }

}