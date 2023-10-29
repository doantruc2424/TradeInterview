package com.example.TradeInterview.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AggregationOrderDto {
    @Id
    private Long userId;
    @NotEmpty(message = "pair is empty")
    private String pair;
    @NotNull(message = "isBid is null")
    private Boolean isBid;
    private BigDecimal price;
    @NotNull(message = "amount is null")
    private BigDecimal amount;
}
