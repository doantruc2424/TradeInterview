package com.example.TradeInterview.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletRepDto {
    private String currency;
    private BigDecimal balance;

    public WalletRepDto() {

    }
}
