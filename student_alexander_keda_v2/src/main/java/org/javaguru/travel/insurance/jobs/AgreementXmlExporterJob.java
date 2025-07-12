package org.javaguru.travel.insurance.jobs;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.services.travel.get.agreement.TravelGetAgreementService;
import org.javaguru.travel.insurance.core.services.travel.get.uuids.TravelGetAgreementUuidsService;
import org.javaguru.travel.insurance.dto.internal.GetAgreementDTOConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@ConditionalOnProperty(
        name = "agreement.xml.exporter.job.enabled",
        havingValue = "true",
        matchIfMissing = false
)
class AgreementXmlExporterJob {

    private final String directoryPath;
    private final TravelGetAgreementUuidsService travelGetAgreementUuidsService;
    private final TravelGetAgreementService travelGetAgreementService;
    private final GetAgreementDTOConverter getAgreementDTOConverter;
    private final XmlMapper xmlMapper;

    public AgreementXmlExporterJob(
            @Value("${agreement.xml.exporter.path:export}") String directoryPath,
            TravelGetAgreementUuidsService travelGetAgreementUuidsService,
            TravelGetAgreementService travelGetAgreementService,
            GetAgreementDTOConverter getAgreementDTOConverter
    ) {
        this.directoryPath = directoryPath;
        this.travelGetAgreementUuidsService = travelGetAgreementUuidsService;
        this.travelGetAgreementService = travelGetAgreementService;
        this.getAgreementDTOConverter = getAgreementDTOConverter;
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Scheduled(fixedRate = 60000)
    void doJob() {
        log.info("AgreementXmlExporterJob started");
        try {
            Files.createDirectories(Path.of(directoryPath));
        } catch (IOException e) {
            log.error("Failed to create directory '{}'. AgreementXmlExporterJob was stopped", directoryPath, e);
            return;
        }
        getAgreementUuids(new TravelGetAllAgreementUuidsCoreCommand())
                .stream()
                .map(this::getAgreementOpt)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::writeAgreementToXmlFile);
        log.info("AgreementXmlExporterJob finished");
    }

    private List<String> getAgreementUuids(TravelGetAllAgreementUuidsCoreCommand command) {
        TravelGetAllAgreementUuidsCoreResult result = travelGetAgreementUuidsService
                .getAgreementUuids(command);
        if (result.hasErrors()) {
            result.errors().forEach(
                    err -> log.warn("Agreement UUIDs request error: code={}, description={}",
                            err.errorCode(),
                            err.description())
            );
            log.error("AgreementXmlExporterJob was stopped due to validation errors");
            return List.of();
        }
        return result.agreementUuids();
    }

    private Optional<AgreementDTO> getAgreementOpt(String uuid) {
        TravelGetAgreementCoreCommand command = getAgreementDTOConverter.buildCommand(uuid);
        TravelGetAgreementCoreResult result = travelGetAgreementService.getAgreement(command);
        if (result.hasErrors()) {
            log.warn("Agreement request error: {}", result.errors());
            return Optional.empty();
        }
        return Optional.of(result.agreement());
    }

    private Path createPath(String uuid) {
        return Path.of(directoryPath +
                "/agreement-" +
                uuid +
                ".xml");
    }

    private void writeAgreementToXmlFile(AgreementDTO agreement) {
        try {
            xmlMapper.writeValue(createPath(agreement.uuid()).toFile(), agreement);
        } catch (IOException e) {
            log.error("Failed to write object to XML file", e);
        }
    }

}
