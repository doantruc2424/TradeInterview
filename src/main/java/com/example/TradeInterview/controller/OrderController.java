package com.example.TradeInterview.controller;

import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.dto.OrderRepDto;
import com.example.TradeInterview.response.ApiResponse;
import com.example.TradeInterview.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid OrderDto order) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", orderService.createOrder(order)));
    }

    @GetMapping("/get_orders")
    public ResponseEntity<ApiResponse> getOrdersByParams(@RequestParam(required = false) String pair, @RequestParam(required = false, defaultValue = "100") Integer limit,
                                                      @RequestParam(required = false,  defaultValue = "0") Integer page) {
        List<OrderRepDto> dto = orderService.getOrdersByParams(pair, limit, page);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", dto));
    }
}
