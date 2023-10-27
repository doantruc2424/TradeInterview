package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class TradeId implements Serializable {
    private Long userBid;

    private Long userAsk;

    public TradeId() {

    }

    public TradeId(Long userBid, Long userAsk) {
        this.userBid = userBid;
        this.userAsk = userAsk;
    }

    public Long getUserBid() {
        return userBid;
    }

    public void setUserBid(Long userBid) {
        this.userBid = userBid;
    }

    public Long getUserAsk() {
        return userAsk;
    }

    public void setUserAsk(Long userAsk) {
        this.userAsk = userAsk;
    }
}
