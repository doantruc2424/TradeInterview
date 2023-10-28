package com.example.TradeInterview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HuobiReturnDataDto {
    private List<HuobiStickerDto> data;
    private String status;
}
