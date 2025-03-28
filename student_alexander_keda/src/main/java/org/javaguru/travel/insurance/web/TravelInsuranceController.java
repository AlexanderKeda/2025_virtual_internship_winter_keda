package org.javaguru.travel.insurance.web;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelInsuranceController {

    private final TravelCalculatePremiumService service;

    @GetMapping("/insurance/travel/web")
    public String showForm(ModelMap modelMap) {
        modelMap.addAttribute("request", new TravelCalculatePremiumRequest());
        modelMap.addAttribute("response", new TravelCalculatePremiumResponse());
        return "travel-calculate-premium";
    }

    @PostMapping("/insurance/travel/web")
    public String processForm(@ModelAttribute(value = "request") TravelCalculatePremiumRequest request,
                              ModelMap modelMap) {
        TravelCalculatePremiumResponse response = service.calculatePremium(request);
        modelMap.addAttribute("response", response);
        return "travel-calculate-premium";
    }
}
