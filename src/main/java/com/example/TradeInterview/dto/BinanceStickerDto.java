package com.example.TradeInterview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceStickerDto {
    private String symbol;
    private String bidPrice;
    private String askPrice;
}
