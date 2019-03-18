package com.kambi.betwizard.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kambi.betwizard.exception.BetWizardException;
import com.kambi.schema.Betoffer;
import com.kambi.schema.Event;
import com.kambi.schema.EventGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class BetWizardDao {

    @Autowired
    private ObjectMapper jsonMapper;

    public List<Event> getEvents() {
        try {
            return jsonMapper.readValue(this.getClass().getClassLoader()
                    .getResourceAsStream("events.json"), new TypeReference<List<Event>>() {
            });
        } catch (IOException e) {
            throw new BetWizardException("", e);
        }
    }

    public List<EventGroup> getEventGroups() {
        try {
            return jsonMapper.readValue(this.getClass().getClassLoader()
                    .getResourceAsStream("eventGroups.json"), new TypeReference<List<EventGroup>>() {
            });
        } catch (IOException e) {
            throw new BetWizardException("", e);
        }
    }


    public List<Betoffer> getBetOffers() {
        try {
            return jsonMapper.readValue(this.getClass().getClassLoader()
                    .getResourceAsStream("betoffers.json"), new TypeReference<List<Betoffer>>() {
            });
        } catch (IOException e) {
            throw new BetWizardException("", e);
        }
    }

}
