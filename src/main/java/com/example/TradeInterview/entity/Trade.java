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
    private Long orderBid;
    @Id
    private Long orderAsk;
    private Long userBid;
    private Long userAsk;
    private String pair;
    private BigDecimal price;
    private BigDecimal amount;
    private Long createdAt;

    public Trade() {

    }

    public Trade(Long orderBid, Long orderAsk, Long userBid, Long userAsk, String pair, BigDecimal price, BigDecimal amount, Long createdAt) {
        this.orderBid = orderBid;
        this.orderAsk = orderAsk;
        this.userBid = userBid;
        this.userAsk = userAsk;
        this.pair = pair;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
