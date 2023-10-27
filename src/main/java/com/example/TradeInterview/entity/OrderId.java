package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderId implements Serializable {
    private Long userId;

    private String pair;

    public OrderId() {

    }

    public OrderId(Long userId, String pair) {
        this.userId = userId;
        this.pair = pair;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }
}
