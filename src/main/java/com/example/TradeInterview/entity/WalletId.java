package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class WalletId implements Serializable {
    private Long userId;
    private String currency;

    public WalletId() {

    }

    public WalletId(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
