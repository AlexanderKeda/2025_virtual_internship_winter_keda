package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalRiskLimitLevelRepository
        extends JpaRepository<MedicalRiskLimitLevel, Long> {

    Optional<MedicalRiskLimitLevel> findByMedicalRiskLimitLevelIc(String medicalRiskLimitIc);

    boolean existsByMedicalRiskLimitLevelIc(String medicalRiskLimitIc);
}
