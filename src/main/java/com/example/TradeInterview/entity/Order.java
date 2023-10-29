package com.example.TradeInterview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private Long userId;
    private String pair;
    private Boolean isBid;
    @Column(precision = 32, scale = 6)
    private BigDecimal price;
    @Column(precision = 32, scale = 6)
    private BigDecimal amount;
    private String status;
    private BigDecimal remain;
    private Long createdAt;
    private Long updatedAt;
}
