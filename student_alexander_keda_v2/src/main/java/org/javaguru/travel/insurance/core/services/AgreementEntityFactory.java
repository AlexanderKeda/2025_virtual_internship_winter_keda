package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementEntityFactory {

    private final AgreementEntityRepository agreementEntityRepository;
    private final PersonEntityFactory personEntityFactory;
    private final SelectedRiskEntityFactory selectedRiskEntityFactory;
    private final AgreementPersonEntityFactory agreementPersonEntityFactory;
    private final AgreementPersonRiskEntityFactory agreementPersonRiskEntityFactory;

    AgreementEntity createAgreementEntity(AgreementDTO agreementDTO) {
        var agreementEntity = saveAgreementEntity(agreementDTO);
        savePersonsData(agreementEntity, agreementDTO);
        saveSelectedRiskEntities(agreementEntity, agreementDTO);
        return agreementEntity;
    }

    private AgreementEntity saveAgreementEntity(AgreementDTO agreementDTO) {
        var agreementEntity = new AgreementEntity(
                null,
                UUID.randomUUID(),
                agreementDTO.agreementDateFrom(),
                agreementDTO.agreementDateTo(),
                agreementDTO.country(),
                agreementDTO.agreementPremium()
        );
        return agreementEntityRepository.save(agreementEntity);
    }

    private List<AgreementPersonEntity> savePersonsData(AgreementEntity agreementEntity, AgreementDTO agreementDTO) {
        return agreementDTO.persons().stream()
                .map(personDTO -> savePersonData(agreementEntity, personDTO))
                .toList();
    }

    private AgreementPersonEntity savePersonData(AgreementEntity agreementEntity, PersonDTO personDTO) {
        var personEntity = personEntityFactory.createPersonEntity(personDTO);
        var agreementPersonEntity = agreementPersonEntityFactory
                .createAgreementPersonEntity(agreementEntity, personEntity, personDTO);
        agreementPersonRiskEntityFactory
                .createAgreementPersonRiskEntities(agreementPersonEntity, personDTO);
        return agreementPersonEntity;
    }

    private List<SelectedRiskEntity> saveSelectedRiskEntities(
            AgreementEntity agreementEntity,
            AgreementDTO agreementDTO
    ) {
        return selectedRiskEntityFactory.createSelectedRiskEntities(agreementEntity, agreementDTO);
    }


}
