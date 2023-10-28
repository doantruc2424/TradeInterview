package com.example.TradeInterview.controller;

import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.service.MatchingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {

    @Autowired
    MatchingService matchingService;

    @PostMapping("/create")
    public void create(@RequestBody @Valid OrderDto order) {
        matchingService.createOrder(order);
    }
}
