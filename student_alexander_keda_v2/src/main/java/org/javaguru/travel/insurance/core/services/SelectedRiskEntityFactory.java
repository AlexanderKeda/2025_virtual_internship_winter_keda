package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.SelectedRisksEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SelectedRiskEntityFactory {

    private final SelectedRisksEntityRepository selectedRisksEntityRepository;

    List<SelectedRiskEntity> createSelectedRiskEntities (
            AgreementEntity agreementEntity,
            AgreementDTO agreementDTO
    ) {
        return agreementDTO.selectedRisks().stream()
                .map(riskIc -> createSelectedRiskEntity(agreementEntity, riskIc))
                .map(selectedRisksEntityRepository::save)
                .toList();
    }

    private SelectedRiskEntity createSelectedRiskEntity(
            AgreementEntity agreementEntity,
            String riskIc
    ) {
        return new SelectedRiskEntity(
                null,
                agreementEntity,
                riskIc
        );
    }



}
