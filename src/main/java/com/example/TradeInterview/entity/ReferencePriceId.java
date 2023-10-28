package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ReferencePriceId {

    private String source;

    private String pair;

    public ReferencePriceId() {

    }

    public ReferencePriceId(String source, String pair) {
        this.source = source;
        this.pair = pair;
    }
}
