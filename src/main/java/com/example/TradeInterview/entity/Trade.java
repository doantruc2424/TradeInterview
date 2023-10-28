package com.example.TradeInterview.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_trade")
@IdClass(TradeId.class)
@Getter
@Setter
public class Trade {
    @Id
    private Long userBid;
    @Id
    private Long userAsk;
    private String pair;
    private Long userMaker;
    private BigDecimal price;
    private BigDecimal amount;
    private Long createdAt;

    public Trade() {

    }

    public Trade(Long userBid, Long userAsk, String pair, Long userMaker, BigDecimal price, BigDecimal amount, Long createdAt) {
        this.userBid = userBid;
        this.userAsk = userAsk;
        this.pair = pair;
        this.userMaker = userMaker;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
