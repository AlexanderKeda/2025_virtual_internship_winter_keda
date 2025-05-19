package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entities.PersonEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgreementEntityFactory {

    private final AgreementEntityRepository agreementEntityRepository;
    private final PersonEntityFactory personEntityFactory;

    AgreementEntity createAgreementEntity(AgreementDTO agreementDTO) {
        var personEntities = savePersons(agreementDTO.persons());
        var agreementEntity = new AgreementEntity(
                null,
                agreementDTO.agreementDateFrom(),
                agreementDTO.agreementDateTo(),
                agreementDTO.country(),
                agreementDTO.agreementPremium()
        );
        return agreementEntityRepository.save(agreementEntity);
    }

    private List<PersonEntity> savePersons (List<PersonDTO> persons) {
        return persons.stream()
                .map(personEntityFactory::createPersonEntity)
                .toList();
    }



}
