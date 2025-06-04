package org.javaguru.travel.insurance.rest.internal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.javaguru.travel.insurance.rest.common.TravelRestRequestExecutionTimeLogger;
import com.google.common.base.Stopwatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/internal")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelGetAgreementRestController {

    private final TravelGetAgreementRequestLogger requestLogger;
    private final TravelGetAgreementResponseLogger responseLogger;
    private final TravelRestRequestExecutionTimeLogger requestExecutionTimeLogger;

    @GetMapping(path = "/agreement/{id}",
            produces = "application/json")
    public TravelGetAgreementResponse getAgreement(@PathVariable String id) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        var response = processGetAgreementRequest(id);
        requestExecutionTimeLogger.log(stopwatch);
        return response;
    }

    private TravelGetAgreementResponse processGetAgreementRequest(String id) {
        requestLogger.log(id);
        var response = new TravelGetAgreementResponse();
        response.setUuid(id);
        responseLogger.log(response);
        return response;
    }
}
