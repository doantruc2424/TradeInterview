package com.example.TradeInterview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HuobiStickerDto {
    private String symbol;
    private String bid;
    private String ask;;
}
