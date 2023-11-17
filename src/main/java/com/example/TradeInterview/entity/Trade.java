package com.example.TradeInterview.entity;


import com.example.TradeInterview.containt.CoinPair;
import com.example.TradeInterview.entity.id.TradeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_trade")
@IdClass(TradeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @Id
    private Long orderBid;
    @Id
    private Long orderAsk;
    private Long userBid;
    private Long userAsk;
    private CoinPair pair;
    @Column(precision = 32, scale = 6)
    private BigDecimal price;
    @Column(precision = 32, scale = 6)
    private BigDecimal amount;
    private Long createdAt;
}
