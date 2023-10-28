package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class WalletId implements Serializable {
    private Long userId;
    private String currency;

    public WalletId() {

    }

    public WalletId(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;
    }
}
