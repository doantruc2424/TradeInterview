package com.example.TradeInterview.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "trade")
@IdClass(TradeId.class)
public class Trade {
    private Long userBid;
    private Long userAsk;
    private String pair;
    private Long userMaker;
    private BigDecimal price;
    private BigDecimal amount;
    private Long createdAt;

    public Trade() {

    }

    public Trade(Long userBid, Long userAsk, String pair, Long userMaker, BigDecimal price, BigDecimal amount, Long createdAt) {
        this.userBid = userBid;
        this.userAsk = userAsk;
        this.pair = pair;
        this.userMaker = userMaker;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getUserBid() {
        return userBid;
    }

    public void setUserBid(Long userBid) {
        this.userBid = userBid;
    }

    public Long getUserAsk() {
        return userAsk;
    }

    public void setUserAsk(Long userAsk) {
        this.userAsk = userAsk;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Long getUserMaker() {
        return userMaker;
    }

    public void setUserMaker(Long userMaker) {
        this.userMaker = userMaker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
