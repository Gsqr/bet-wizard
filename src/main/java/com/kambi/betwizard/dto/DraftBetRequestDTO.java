package com.kambi.betwizard.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DraftBetRequestDTO {

    private BigDecimal stake;

    private BigDecimal payout;

    private List<String> eventPath;

    private BigDecimal maxOdds;

    private Integer slipSize;

    private Date fromDate;

    private Date toDate;

    private Boolean combination;

    public boolean isCombination() {
        return combination;
    }

    public void setCombination(Boolean combination) {
        this.combination = combination;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }

    public List<String> getEventPath() {
        return eventPath;
    }

    public void setEventPath(List<String> eventPath) {
        this.eventPath = eventPath;
    }

    public BigDecimal getMaxOdds() {
        return maxOdds;
    }

    public void setMaxOdds(BigDecimal maxOdds) {
        this.maxOdds = maxOdds;
    }

    public Integer getSlipSize() {
        return slipSize;
    }

    public void setSlipSize(Integer slipSize) {
        this.slipSize = slipSize;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
