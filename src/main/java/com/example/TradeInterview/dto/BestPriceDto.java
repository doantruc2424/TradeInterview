package com.example.TradeInterview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BestPriceDto {
    private String symbol;
    private BigDecimal bidPrice;
    private String bidSource;
    private BigDecimal askPrice;
    private String askSource;
    private Long updatedAt;
}
