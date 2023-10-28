package com.example.TradeInterview.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BestPriceDto {
    private String symbol;
    private BigDecimal bidPrice;
    private String bidSource;
    private BigDecimal askPrice;
    private String askSource;
    private Long updatedAt;

    public BestPriceDto(String symbol) {

    }

    public BestPriceDto(String symbol, BigDecimal bidPrice, String bidSource, BigDecimal askPrice, String askSource, Long updatedAt) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.bidSource = bidSource;
        this.askPrice = askPrice;
        this.askSource = askSource;
        this.updatedAt = updatedAt;
    }
}
