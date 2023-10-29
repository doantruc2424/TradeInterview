package com.example.TradeInterview.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDto {
    @Id
    private Long userId;
    @NotEmpty(message = "pair is empty")
    private String pair;
    @NotNull(message = "isBid is null")
    private Boolean isBid;
    @NotNull(message = "price is null")
    private BigDecimal price;
    @NotNull(message = "amount is null")
    private BigDecimal amount;

    public OrderDto() {

    }

    public OrderDto(Long userId, String pair, Boolean isBid, BigDecimal price, BigDecimal amount) {
        this.userId = userId;
        this.pair = pair;
        this.isBid = isBid;
        this.price = price;
        this.amount = amount;
    }
}
