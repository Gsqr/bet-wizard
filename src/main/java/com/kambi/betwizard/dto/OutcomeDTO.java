package com.kambi.betwizard.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class OutcomeDTO {

    public OutcomeDTO() {

    }

    public OutcomeDTO(String eventName, String name, BigDecimal odds, String betOfferType) {
        this.eventName = eventName;
        this.name = name;
        this.odds = odds;
        this.betOfferType = betOfferType;
    }

    private String eventName;

    private String name;

    private BigDecimal odds;

    private String betOfferType;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public String getBetOfferType() {
        return betOfferType;
    }

    public void setBetOfferType(String betOfferType) {
        this.betOfferType = betOfferType;
    }

    @Override
    public String toString() {
        return "OutcomeDTO{" +
                "eventName='" + eventName + '\'' +
                ", name='" + name + '\'' +
                ", odds=" + odds +
                ", betOfferType='" + betOfferType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutcomeDTO that = (OutcomeDTO) o;
        return Objects.equals(eventName, that.eventName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(odds, that.odds) &&
                Objects.equals(betOfferType, that.betOfferType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, name, odds, betOfferType);
    }
}
