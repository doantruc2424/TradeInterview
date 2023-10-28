package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class OrderId implements Serializable {
    private Long userId;
    private String pair;

    public OrderId() {
    }

    public OrderId(Long userId, String pair) {
        this.userId = userId;
        this.pair = pair;
    }
}
