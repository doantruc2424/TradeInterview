package com.example.TradeInterview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDto {
    private Long userId;
    @NotEmpty(message = "pair is empty")
    @Max(value = 10)
    private String pair;
    @NotNull(message = "isBid is null")
    private Boolean isBid;
    @NotNull(message = "price is null")
    private BigDecimal price;
    @NotNull(message = "amount is null")
    private BigDecimal amount;

    private String status;

    private BigDecimal remain;
    private Long createdAt;
    private Long updatedAt;

    public OrderDto() {

    }

    public OrderDto(Long userId, String pair, Boolean isBid, BigDecimal price, BigDecimal amount, Long createdAt, Long updatedAt) {
        this.userId = userId;
        this.pair = pair;
        this.isBid = isBid;
        this.price = price;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
