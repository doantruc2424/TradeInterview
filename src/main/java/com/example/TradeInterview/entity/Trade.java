package com.example.TradeInterview.entity;


import com.example.TradeInterview.entity.id.TradeId;
import jakarta.persistence.*;
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
    @Column(precision = 32, scale = 6)
    private BigDecimal price;
    @Column(precision = 32, scale = 6)
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
