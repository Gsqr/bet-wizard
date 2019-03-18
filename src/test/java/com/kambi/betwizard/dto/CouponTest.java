package com.kambi.betwizard.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class CouponTest {

    @Test
    public void generateCouponJson() throws JsonProcessingException {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setOutcomes(IntStream.range(0,15).mapToObj(i -> outcomeFactory()).collect(Collectors.toList()));

        System.out.println(new ObjectMapper().writeValueAsString(couponDTO));
    }

    private OutcomeDTO outcomeFactory() {
        Long x = new Random().nextLong();
        OutcomeDTO outcomeDTO = new OutcomeDTO();
        outcomeDTO.setBetOfferType("betofferType " + x);
        outcomeDTO.setEventName("eventName " + x);
        outcomeDTO.setName("outcomeName " + x);
        outcomeDTO.setOdds(BigDecimal.valueOf(x));

        return outcomeDTO;
    }

}