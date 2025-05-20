package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedRisksEntityRepository
        extends JpaRepository<SelectedRiskEntity, Long> {
}
