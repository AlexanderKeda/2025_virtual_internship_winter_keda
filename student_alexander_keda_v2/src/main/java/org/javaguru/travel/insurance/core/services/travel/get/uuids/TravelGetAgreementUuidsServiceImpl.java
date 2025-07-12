package org.javaguru.travel.insurance.core.services.travel.get.uuids;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetAgreementUuidsServiceImpl
        implements TravelGetAgreementUuidsService {

    private final AgreementEntityRepository agreementEntityRepository;

    @Override
    public TravelGetAllAgreementUuidsCoreResult getAgreementUuids(TravelGetAllAgreementUuidsCoreCommand command) {
        return buildCoreResult();
    }

    private TravelGetAllAgreementUuidsCoreResult buildCoreResult() {
        var agreementUuids = agreementEntityRepository.findAllUuids();
        return new TravelGetAllAgreementUuidsCoreResult(null, agreementUuids);
    }
}
