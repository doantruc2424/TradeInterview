package com.example.TradeInterview.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "reference_price")
@IdClass(ReferencePriceId.class)
public class ReferencePrice {
    private String source;
    private BigDecimal price;
    private String pair;
    private Long updatedAt;

    public ReferencePrice(String source, BigDecimal price, String pair, Long updatedAt) {
        this.source = source;
        this.price = price;
        this.pair = pair;
        this.updatedAt = updatedAt;
    }

    public ReferencePrice() {

    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
