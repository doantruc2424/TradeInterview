package com.example.TradeInterview.controller;

import com.example.TradeInterview.dto.BestPriceDto;
import com.example.TradeInterview.response.ApiResponse;
import com.example.TradeInterview.service.ExternalPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/external_price")
public class PriceAggregationController {

    @Autowired
    ExternalPriceService externalPriceService;

    @GetMapping("/best_prices")
    public ResponseEntity<ApiResponse> findBestPrices(@RequestParam(required = false) String pair) {
        List<BestPriceDto> dto = externalPriceService.findBestPrice(pair);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", dto));
    }
}
