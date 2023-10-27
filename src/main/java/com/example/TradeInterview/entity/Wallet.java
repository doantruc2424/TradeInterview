package com.example.TradeInterview.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@IdClass(WalletId.class)
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
