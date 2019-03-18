package com.kambi.betwizard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kambi.schema.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.IntStream;


@SuppressWarnings("Duplicates")
public class BootStrap {

    @Autowired
    private ObjectMapper jsonMapper;


    @PostConstruct
    public void init() throws IOException {
        generateEventGroupData();
        generateEventData();
        generateBetOfferData();
    }

    private Betoffer getBetoffer(long eventId, long betOfferId, long outcomeId, long outcomeId1, long outcomeId2,
                                 Outcome.OutcomeType outcomeType1, Outcome.OutcomeType outcomeType2, Outcome.OutcomeType outcomeType3,
                                 BetOfferType betOfferType) {
        Outcome outcome = new Outcome();
        outcome.setOdds(new BigDecimal(1.35).setScale(2, RoundingMode.HALF_UP));
        outcome.setOutcomeType(outcomeType1);
        outcome.setOutcomeId(outcomeId2);
        outcome.setProbability(new BigDecimal(0.40).setScale(2, RoundingMode.HALF_UP));

        Outcome outcomeX = new Outcome();
        outcomeX.setOdds(new BigDecimal(1.88).setScale(2, RoundingMode.HALF_UP));
        outcomeX.setOutcomeType(outcomeType2);
        outcomeX.setOutcomeId(outcomeId1);
        outcomeX.setProbability(new BigDecimal(0.35).setScale(2, RoundingMode.HALF_UP));


        Outcome outcome2 = new Outcome();
        outcome2.setOdds(new BigDecimal(2.12).setScale(2, RoundingMode.HALF_UP));
        outcome2.setOutcomeType(outcomeType3);
        outcome2.setOutcomeId(outcomeId);
        outcome2.setProbability(new BigDecimal(0.25).setScale(2, RoundingMode.HALF_UP));

        Betoffer betoffer = new Betoffer();
        betoffer.getOutcomes().add(outcome);

        betoffer.getOutcomes().add(outcomeX);
        betoffer.getOutcomes().add(outcome2);
        betoffer.setBetOfferId(betOfferId);
        betoffer.setEventId(eventId);
        betoffer.setBetOfferType(betOfferType);
        betoffer.setLine(new BigDecimal(2.2).setScale(2, RoundingMode.HALF_UP));
        return betoffer;
    }

    private Betoffer getBetoffer(long eventId, long betOfferId, long outcomeId, long outcomeId1,
                                 Outcome.OutcomeType outcomeType1, Outcome.OutcomeType outcomeType2, BetOfferType betOfferType) {

        Outcome outcome1 = new Outcome();
        outcome1.setOdds(new BigDecimal(1.88).setScale(2, RoundingMode.HALF_UP));
        outcome1.setOutcomeType(outcomeType1);
        outcome1.setOutcomeId(outcomeId1);
        outcome1.setProbability(new BigDecimal(0.35).setScale(2, RoundingMode.HALF_UP));


        Outcome outcome2 = new Outcome();
        outcome2.setOdds(new BigDecimal(2.12).setScale(2, RoundingMode.HALF_UP));
        outcome2.setOutcomeType(outcomeType2);
        outcome2.setOutcomeId(outcomeId);
        outcome2.setProbability(new BigDecimal(0.25).setScale(2, RoundingMode.HALF_UP));

        Betoffer betoffer = new Betoffer();
        betoffer.getOutcomes().add(outcome1);
        betoffer.getOutcomes().add(outcome2);
        betoffer.setBetOfferId(betOfferId);
        betoffer.setEventId(eventId);
        betoffer.setBetOfferType(betOfferType);
        betoffer.setLine(new BigDecimal(2.2).setScale(2, RoundingMode.HALF_UP));
        return betoffer;
    }

    private Event getEvent(long eventId, long eventGroupId, String eventName, String sportId, String eventGroupName) {
        Event event = new Event();
        event.setEventId(eventId);
        event.setEventGroupId(eventGroupId);
        event.setEventName(eventName);
        event.setSportId(sportId);
        event.setStartDate(new Date());
        event.setSettledDate(new Date());

        EventGroup eventGroup = new EventGroup();
        eventGroup.setEventGroupId(eventGroupId);
        eventGroup.setEventGroupName(eventGroupName);
        event.getEventTreePath().add(eventGroup);
        return event;
    }

    private void generateBetOfferData() throws IOException {
        List<Betoffer> betoffers = new ArrayList<>();
        Long eventId = 1000000001L;
        Long betOfferId = 1000000001L;
        Long outcomeId = 1000000001L;
        for (int i = 0; i < 5; i++) {
            Betoffer betoffer = getBetoffer(eventId, betOfferId, outcomeId, ++outcomeId,
                    ++outcomeId,
                    Outcome.OutcomeType.THW_HOME_WIN,
                    Outcome.OutcomeType.THW_AWAY_WIN,
                    Outcome.OutcomeType.THW_DRAW,
                    BetOfferType.THREE_WAY);

            Betoffer betoffer1 = getBetoffer(eventId, ++betOfferId, ++outcomeId, ++outcomeId,
                    Outcome.OutcomeType.THW_HOME_WIN,
                    Outcome.OutcomeType.THW_DRAW,
                    BetOfferType.TWO_WAY);
            betoffers.add(betoffer);
            betoffers.add(betoffer1);
            eventId++;
            betOfferId++;
            outcomeId++;
        }
        jsonMapper.writeValue(new File("betoffers.json"), betoffers);
    }

    private void generateEventData() throws IOException {

        List<Event> events = new ArrayList<>();

        Map<Integer, String> eventsName = new HashMap<>();
        eventsName.put(0, "Bournemouth-Newcastle United");
        eventsName.put(1, "Burnley-Leicester City");
        eventsName.put(2, "West Ham-Huddersfield Town");
        eventsName.put(3, "Fulham-Liverpool");
        eventsName.put(4, "Everton-Chelsea");
        IntStream.range(0, 5).forEach(i -> events.addAll(event(i, eventsName)));

        jsonMapper.writeValue(new File("events.json"), events);

    }

    private void generateEventGroupData() throws IOException {
        List<EventGroup> list = new ArrayList<>();
        EventGroup eventGroup4 = new EventGroup();
        eventGroup4.setEventGroupId(1000000001L);
        eventGroup4.setEventGroupName("Premier League");
        list.add(eventGroup4);
        jsonMapper.writeValue(new File("eventGroups.json"), list);

    }

    private EventGroup eventGroup(int i) {
        EventGroup eventGroup4 = new EventGroup();
        eventGroup4.setEventGroupId(1000000001L + i);
        eventGroup4.setEventGroupName("EVENTGROUPNAME" + i);
        return eventGroup4;
    }

    private List<Event> event(int i, Map<Integer, String> eventsName) {
        List<Event> events = new ArrayList<>();
        events.add(getEvent(1000000001L + i, 1000000001L, eventsName.get(i),
                "Football", "Premier League"));
        return events;
    }
}
