package org.javaguru.travel.insurance.core.services.travel.get.uuids;

import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;

public interface TravelGetAgreementUuidsService {

    TravelGetAllAgreementUuidsCoreResult getAgreementUuids(
            TravelGetAllAgreementUuidsCoreCommand command
    );

}
