package org.javaguru.travel.insurance.rest;

import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumController {

	private final TravelCalculatePremiumService calculatePremiumService;
	private final TravelCalculatePremiumRequestLogger requestLogger;
	private final TravelCalculatePremiumResponseLogger responseLogger;
	private final TravelCalculatePremiumRequestExecutionTimeLogger requestExecutionTimeLogger;

	@PostMapping(path = "/",
			consumes = "application/json",
			produces = "application/json")
	public TravelCalculatePremiumResponse calculatePremium(@RequestBody TravelCalculatePremiumRequest request) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelCalculatePremiumResponse response = processRequest(request);
		requestExecutionTimeLogger.log(stopwatch);
		return response;
	}

	private TravelCalculatePremiumResponse processRequest(TravelCalculatePremiumRequest request) {
		requestLogger.log(request);
		TravelCalculatePremiumResponse response = calculatePremiumService.calculatePremium(request);
		responseLogger.log(response);
		return response;
	}

}