package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class TradeId implements Serializable {
    private Long userBid;

    private Long userAsk;

    public TradeId() {

    }

    public TradeId(Long userBid, Long userAsk) {
        this.userBid = userBid;
        this.userAsk = userAsk;
    }
}
