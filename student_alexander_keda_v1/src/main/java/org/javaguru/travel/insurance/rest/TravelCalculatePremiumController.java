package org.javaguru.travel.insurance.rest;

import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumController {

	private final TravelCalculatePremiumService calculatePremiumService;
	private final TravelCalculatePremiumRequestLogger requestLogger;
	private final TravelCalculatePremiumResponseLogger responseLogger;
	private final TravelCalculatePremiumRequestExecutionTimeLogger requestExecutionTimeLogger;

	@PostMapping(path = "/",
			consumes = "application/json",
			produces = "application/json")
	public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelCalculatePremiumResponseV1 response = processRequest(request);
		requestExecutionTimeLogger.log(stopwatch);
		return response;
	}

	private TravelCalculatePremiumResponseV1 processRequest(TravelCalculatePremiumRequestV1 request) {
		requestLogger.log(request);
		TravelCalculatePremiumResponseV1 response = calculatePremiumService.calculatePremium(request);
		responseLogger.log(response);
		return response;
	}

}