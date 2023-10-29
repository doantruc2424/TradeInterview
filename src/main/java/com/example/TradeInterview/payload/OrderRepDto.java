package com.example.TradeInterview.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRepDto {
    private Long id;
    private Long userId;
    private String pair;
    private Boolean isBid;
    @Column(precision = 8)
    private BigDecimal price;
    @Column(precision = 8)
    private BigDecimal amount;

    private String status;

    private BigDecimal remain;
    private Long createdAt;
    private Long updatedAt;

    public OrderRepDto() {

    }
}
