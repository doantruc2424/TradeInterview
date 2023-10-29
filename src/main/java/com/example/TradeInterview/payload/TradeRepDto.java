package com.example.TradeInterview.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TradeRepDto {
    private Long orderId;
    private String pair;
    private BigDecimal price;
    private BigDecimal amount;
    private Boolean isBid;
    private Long createdAt;
}
