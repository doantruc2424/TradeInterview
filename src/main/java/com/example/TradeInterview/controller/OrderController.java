package com.example.TradeInterview.controller;

import com.example.TradeInterview.Exception.BalanceNotEnoughException;
import com.example.TradeInterview.Exception.WalletNotFoundException;
import com.example.TradeInterview.dto.AggregationOrderDto;
import com.example.TradeInterview.dto.BestPriceDto;
import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.payload.OrderRepDto;
import com.example.TradeInterview.payload.ApiResponse;
import com.example.TradeInterview.service.ExternalPriceService;
import com.example.TradeInterview.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderService orderService;
    @Autowired
    ExternalPriceService externalPriceService;

    /**
     * 1. User able to buy/sell the supported crypto trading pairs
     * @param order
     * @return
     * @throws WalletNotFoundException
     * @throws BalanceNotEnoughException
     */
    @PostMapping("/new")
    public ResponseEntity<ApiResponse> create(@RequestBody @Validated OrderDto order) throws WalletNotFoundException, BalanceNotEnoughException {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", orderService.createOrder(order)));
    }


    /**
     * 3. Create an api which allows users to trade based on the latest best aggregated
     * price.
     * @param order
     * @return
     * @throws WalletNotFoundException
     * @throws BalanceNotEnoughException
     */
    @PostMapping("/aggregation_price/new")
    public ResponseEntity<ApiResponse> aggregationPriceNew(@RequestBody @Validated AggregationOrderDto order) throws WalletNotFoundException, BalanceNotEnoughException {
        List<BestPriceDto> bestPriceDtos = externalPriceService.findBestPrice(order.getPair());
        if(bestPriceDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Error", "Can not found reference price for " + order.getPair(), ""));
        }
        //Bid Price use for SELL order, Ask Price use for BUY order
        order.setPrice(order.getIsBid() ? bestPriceDtos.get(0).getAskPrice() : bestPriceDtos.get(0).getBidPrice());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", orderService.createOrder(order)));
    }

    /**
     * User get order
     * @param pair
     * @param limit
     * @param page
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getOrdersByParams(@RequestParam(required = false) String pair, @RequestParam(required = false, defaultValue = "100") Integer limit,
                                                      @RequestParam(required = false,  defaultValue = "0") Integer page) {
        List<OrderRepDto> dto = orderService.getOrdersByParams(pair, limit, page);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", dto));
    }
}
