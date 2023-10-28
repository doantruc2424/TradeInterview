package com.example.TradeInterview.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TradeRepDto {
    private String pair;
    private BigDecimal price;
    private BigDecimal amount;
    private Long createdAt;

    public TradeRepDto() {

    }
}
