package com.example.TradeInterview.entity;

import com.example.TradeInterview.containt.CoinPair;
import com.example.TradeInterview.containt.OrderStatus;
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
    private CoinPair pair;
    private Boolean isBid;
    @Column(precision = 32, scale = 6)
    private BigDecimal price;
    @Column(precision = 32, scale = 6)
    private BigDecimal amount;
    private OrderStatus status;
    private BigDecimal remain;
    private Long createdAt;
    private Long updatedAt;
}
