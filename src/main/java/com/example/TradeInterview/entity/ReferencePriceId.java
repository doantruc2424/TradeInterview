package com.example.TradeInterview.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReferencePriceId {

    private String source;

    private String pair;

    public ReferencePriceId() {

    }

    public ReferencePriceId(String source, String pair) {
        this.source = source;
        this.pair = pair;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }
}
