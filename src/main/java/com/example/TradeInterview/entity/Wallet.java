package com.example.TradeInterview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_wallet")
@IdClass(WalletId.class)
@Getter
@Setter
public class Wallet {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Id
    @Column(name = "currency", length = 10)
    private String currency;
    @Column(name = "balance")
    private BigDecimal balance;

    public Wallet() {

    }

    public Wallet(Long userId, String currency, BigDecimal balance) {
        this.userId = userId;
        this.currency = currency;
        this.balance = balance;
    }
}
