package com.kambi.betwizard.service;

import com.github.dakusui.combinatoradix.Combinator;
import com.kambi.betwizard.dao.BetWizardDao;
import com.kambi.betwizard.dto.CouponDTO;
import com.kambi.betwizard.dto.DraftBetRequestDTO;
import com.kambi.betwizard.dto.OutcomeDTO;
import com.kambi.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
@Service
public class BetWizardService {

    @Autowired
    private BetWizardDao betWizardDao;

    public List<Event> getEvents() {
        return betWizardDao.getEvents();
    }

    public List<EventGroup> getEventGroups() {
        return betWizardDao.getEventGroups();
    }

    public List<Betoffer> getBetOffers() {
        return betWizardDao.getBetOffers();
    }

    public CouponDTO generateMockCouponSingles(DraftBetRequestDTO draftBetRequestDTO) {
        CouponDTO couponDTO = new CouponDTO();
        OutcomeDTO outcomeDTO = new OutcomeDTO("Bournemouth-Newcastle United", "over 1.5", new BigDecimal(2.01), BetOfferType.TOTAL_GOALS.name());
        OutcomeDTO outcomeDTO1 = new OutcomeDTO("Burnley-Leicester City", "Home Team to win by 3 or more", new BigDecimal(1.90), BetOfferType.WINNING_MARGIN.name());
        OutcomeDTO outcomeDTO2 = new OutcomeDTO("West Ham-Huddersfield Town", "West Ham", new BigDecimal(1.94), BetOfferType.HALF_TIME.name());
        OutcomeDTO outcomeDTO3 = new OutcomeDTO("Fulham-Liverpool", "Liverpool 4-1", new BigDecimal(1.84), BetOfferType.THREE_WAY_HANDICAP.name());
        OutcomeDTO outcomeDTO4 = new OutcomeDTO("Everton-Chelsea", "Everton", new BigDecimal(2.50), BetOfferType.FULL_TIME.name());

        List<OutcomeDTO> outcomes = Arrays.asList(outcomeDTO, outcomeDTO1, outcomeDTO2, outcomeDTO3, outcomeDTO4);

        BigDecimal totalPayout = outcomes.stream().map(o -> o.getOdds().multiply(draftBetRequestDTO.getStake())).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_EVEN);

        couponDTO.setOutcomes(outcomes);
        couponDTO.setTotalOdds(BigDecimal.ZERO);
        couponDTO.setTotalPayout(totalPayout);
        couponDTO.setTotalStake(draftBetRequestDTO.getStake());
        couponDTO.setCombination(false);
        return couponDTO;

    }

    public CouponDTO generateMockCouponCombination(DraftBetRequestDTO draftBetRequestDTO) {
        CouponDTO couponDTO = new CouponDTO();
        OutcomeDTO outcomeDTO = new OutcomeDTO("FC Barcelona - Juventus", "over 1.5", new BigDecimal(1.64), BetOfferType.TOTAL_GOALS.name());
        OutcomeDTO outcomeDTO1 = new OutcomeDTO("Bayern Munchen - Liverpool", "Liverpool", new BigDecimal(1.74), BetOfferType.FULL_TIME.name());
        OutcomeDTO outcomeDTO2 = new OutcomeDTO("Chelsea - Lyon", "Chelsea", new BigDecimal(1.15), BetOfferType.HALF_TIME.name());
        OutcomeDTO outcomeDTO3 = new OutcomeDTO("Paris - Monaco", "Paris 4-1", new BigDecimal(1.64), BetOfferType.THREE_WAY_HANDICAP.name());
        OutcomeDTO outcomeDTO4 = new OutcomeDTO("Roma - Real Madrid", "3-0", new BigDecimal(1.89), BetOfferType.CORRECT_SCORE.name());

        List<OutcomeDTO> outcomes = Arrays.asList(outcomeDTO, outcomeDTO1, outcomeDTO2, outcomeDTO3, outcomeDTO4);


        BigDecimal odds = outcomes.stream().map(OutcomeDTO::getOdds).reduce(BigDecimal::multiply).orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal totalPayout = odds.multiply(draftBetRequestDTO.getStake()).setScale(2, RoundingMode.HALF_EVEN);

        couponDTO.setOutcomes(outcomes);
        couponDTO.setTotalOdds(odds);
        couponDTO.setTotalPayout(totalPayout);
        couponDTO.setTotalStake(draftBetRequestDTO.getStake());
        couponDTO.setCombination(true);
        return couponDTO;
    }

    public CouponDTO generateCouponDate(final DraftBetRequestDTO draftBetRequestDto) {
        CouponDTO couponDTO = new CouponDTO();
        Map<Long, Long> outcomeEventIdMap = new HashMap<>();
        Map<Long, Long> outcomeBetOfferIdMap = new HashMap<>();
        List<Event> events = this.getEvents().stream().filter(event -> event.getStartDate().after(draftBetRequestDto.getFromDate())
                && event.getStartDate().before(draftBetRequestDto.getToDate())).collect(Collectors.toList());

        Map<Long, Event> eventMap = events.stream().collect(Collectors.toMap(Event::getEventId, Function.identity()));
        List<Betoffer> betoffers = this.getBetOffers().stream().filter(betoffer -> events.stream().anyMatch(event ->
                event.getEventId().longValue() == betoffer.getEventId().longValue())).collect(Collectors.toList());
        Map<Long, Betoffer> betOfferMap = betoffers.stream().collect(Collectors.toMap(Betoffer::getBetOfferId, Function.identity()));

        betoffers.forEach(betoffer -> {
            betoffer.getOutcomes().forEach(outcome -> {
                outcomeEventIdMap.put(outcome.getOutcomeId(), betoffer.getEventId());
                outcomeBetOfferIdMap.put(outcome.getOutcomeId(), betoffer.getBetOfferId());
            });
        });

        Map<Long, List<Outcome>> mapOutcomeList = new HashMap<>();
        betoffers.stream().forEach(betoffer -> {
            betoffer.getOutcomes().stream().forEach(outcome -> {
                List<Outcome> outcomeList = mapOutcomeList.getOrDefault(betoffer.getEventId(), new ArrayList<>());
                outcomeList.add(outcome);
                mapOutcomeList.put(betoffer.getEventId(), outcomeList);
            });
        });

        List<Outcome> outcomes = new ArrayList<>();
        mapOutcomeList.values().stream().forEach(outcomesList -> outcomes.addAll(outcomesList));
        for (List<Outcome> outcomeList : new Combinator<>(outcomes, 5)) {
            if(!isBelongToSameId(outcomeList, outcomeEventIdMap) || !isBelongToSameId(outcomeList, outcomeBetOfferIdMap)) {
                continue;
            }
            //BigDecimal oddProbabilty = each.stream().map(outcome -> outcome.getOdds()).reduce(BigDecimal::multiply).orElse(BigDecimal.ZERO);
            BigDecimal totalPayout = outcomeList.stream().map(o -> o.getOdds().multiply(draftBetRequestDto.getStake())).
                    reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            if (totalPayout.compareTo(draftBetRequestDto.getPayout()) >= 0) {
                List<OutcomeDTO> outcomesfinal = new ArrayList<>();
                outcomeList.forEach(outcome -> {
                    Event event = eventMap.get(outcomeEventIdMap.get(outcome.getOutcomeId()));
                    Betoffer betoffer = betOfferMap.get(outcomeBetOfferIdMap.get(outcome.getOutcomeId()));
                    OutcomeDTO outcomeDTO = new OutcomeDTO(event.getEventName(),
                            outcome.getOutcomeName(),
                            outcome.getOdds(), betoffer.getBetOfferType().name());
                    outcomesfinal.add(outcomeDTO);
                });
                couponDTO.setOutcomes(outcomesfinal);
                couponDTO.setTotalOdds(BigDecimal.ZERO);
                couponDTO.setTotalPayout(totalPayout);
                couponDTO.setTotalStake(draftBetRequestDto.getStake());
                couponDTO.setCombination(false);
                return couponDTO;
            }
        }

        return couponDTO;
    }

    public boolean isBelongToSameId(List<Outcome> outcomes, Map<Long, Long> outcomeEventIdMap) {
        Set<Long> events = new HashSet<>();
        outcomes.stream().forEach(outcome -> {
            Long eventId = outcomeEventIdMap.get(outcome.getOutcomeId());
            events.add(eventId);
        });
        return events.size() == outcomes.size();
    }
}
