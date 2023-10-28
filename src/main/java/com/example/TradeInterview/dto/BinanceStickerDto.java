package com.example.TradeInterview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceStickerDto {
    private String symbol;
    private String bidPrice;
    private String askPrice;
    private String bidQty;
    private String askQty;
}
