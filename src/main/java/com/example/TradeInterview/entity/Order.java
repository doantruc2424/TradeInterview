package com.example.TradeInterview.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "order")
@IdClass(OrderId.class)
public class Order {
    private Long userId;
    private String pair;
    private Boolean isBid;
    private BigDecimal price;
    private BigDecimal amount;
    private Long createdAt;
    private Long updatedAt;

    public Order() {

    }
    public Order(Long userId, String pair, Boolean isBid, BigDecimal price, BigDecimal amount, Long createdAt, Long updatedAt) {
        this.userId = userId;
        this.pair = pair;
        this.isBid = isBid;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public boolean isBid() {
        return isBid;
    }

    public void setBid(Boolean bid) {
        isBid = bid;
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

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
