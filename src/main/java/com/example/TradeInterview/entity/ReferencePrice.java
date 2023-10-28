package com.example.TradeInterview.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_reference_price")
@IdClass(ReferencePriceId.class)
@Getter
@Setter
public class ReferencePrice {
    @Id
    private String source;
    @Id
    private String pair;
    @Column(precision = 8)
    private BigDecimal bidPrice;
    @Column(precision = 8)
    private BigDecimal askPrice;
    private Long updatedAt;

    public ReferencePrice(String source, String pair, BigDecimal bidPrice, BigDecimal askPrice, Long updatedAt) {
        this.source = source;
        this.pair = pair;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        this.updatedAt = updatedAt;
    }

    public ReferencePrice(String source, String pair) {
        this.source = source;
        this.pair = pair;
    }

    public ReferencePrice() {

    }
}
