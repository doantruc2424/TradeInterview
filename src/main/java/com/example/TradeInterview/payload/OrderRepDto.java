package com.example.TradeInterview.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
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
}
