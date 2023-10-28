package com.example.TradeInterview.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
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
    private BigDecimal bidPrice;
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
