package com.example.TradeInterview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_order")
@IdClass(OrderId.class)
@Getter
@Setter
public class Order {
    @Id
    private Long userId;
    @Id
    private String pair;
    private Boolean isBid;
    private BigDecimal price;
    private BigDecimal amount;

    private String status;

    private BigDecimal remain;
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
}
