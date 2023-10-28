package com.example.TradeInterview.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_order")
@Getter
@Setter
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private Long userId;
    private String pair;
    private Boolean isBid;
    @Column(precision = 8)
    private BigDecimal price;
    @Column(precision = 8)
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
