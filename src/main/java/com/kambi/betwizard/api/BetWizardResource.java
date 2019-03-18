package com.kambi.betwizard.api;

import com.kambi.betwizard.dto.CouponDTO;
import com.kambi.betwizard.dto.DraftBetRequestDTO;

import com.kambi.betwizard.service.BetWizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BetWizardResource {

    @Autowired
    private BetWizardService betWizardService;

    @PostMapping("/api/v1/generateBetCoupon")
    public @ResponseBody
    CouponDTO generateBetCoupon(@RequestBody final DraftBetRequestDTO draftBetRequestDTO) {
        if (draftBetRequestDTO.isCombination()) {
            return betWizardService.generateMockCouponCombination(draftBetRequestDTO);
        } else {
            return betWizardService.generateMockCouponSingles(draftBetRequestDTO);
        }
    }

    @PostMapping("/api/v2/generateBetCoupon")
    public @ResponseBody
    CouponDTO generateBetCouponV2(@RequestBody final DraftBetRequestDTO draftBetRequestDTO) {
        if (draftBetRequestDTO.isCombination()) {
            return betWizardService.generateMockCouponCombination(draftBetRequestDTO);
        } else {
            return betWizardService.generateCouponDate(draftBetRequestDTO);
        }
    }
}
