package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class TradeId implements Serializable {
    private Long orderBid;

    private Long orderAsk;

    public TradeId() {

    }

    public TradeId(Long orderBid, Long orderAsk) {
        this.orderBid = orderBid;
        this.orderAsk = orderAsk;
    }
}
