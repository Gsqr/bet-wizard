package com.kambi.betwizard.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CouponDTO {

    private List<OutcomeDTO> outcomes;
    private BigDecimal totalPayout;
    private BigDecimal totalOdds;
    private BigDecimal totalStake;
    private boolean combination;

    public boolean isCombination() {
        return combination;
    }

    public void setCombination(boolean combination) {
        this.combination = combination;
    }

    public BigDecimal getTotalStake() {
        return totalStake;
    }

    public void setTotalStake(BigDecimal totalStake) {
        this.totalStake = totalStake;
    }

    public BigDecimal getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(BigDecimal totalPayout) {
        this.totalPayout = totalPayout;
    }

    public BigDecimal getTotalOdds() {
        return totalOdds;
    }

    public void setTotalOdds(BigDecimal totalOdds) {
        this.totalOdds = totalOdds;
    }

    public List<OutcomeDTO> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(final List<OutcomeDTO> outcomes) {
        this.outcomes = outcomes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponDTO couponDTO = (CouponDTO) o;
        return Objects.equals(outcomes, couponDTO.outcomes) &&
               Objects.equals(totalPayout, couponDTO.totalPayout) &&
               Objects.equals(totalOdds, couponDTO.totalOdds) &&
               Objects.equals(totalStake, couponDTO.totalStake);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outcomes, totalPayout, totalOdds, totalStake);
    }

    @Override
    public String toString() {
        return "CouponDTO{" +
               "outcomes=" + outcomes +
               ", totalPayout=" + totalPayout +
               ", totalOdds=" + totalOdds +
               ", totalStake=" + totalStake +
               '}';
    }
}
