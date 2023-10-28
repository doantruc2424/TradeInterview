package com.example.TradeInterview.controller;

import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.dto.OrderRepDto;
import com.example.TradeInterview.dto.TradeRepDto;
import com.example.TradeInterview.response.ApiResponse;
import com.example.TradeInterview.service.OrderService;
import com.example.TradeInterview.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/trade")
public class TradeController {

    @Autowired
    TradeService tradeService;

    /**
     * 2. User able to see the list of trading transactions
     * @param pair
     * @param limit
     * @param page
     * @return
     */
    @GetMapping("/get_trades")
    public ResponseEntity<ApiResponse> getOrdersByParams(@RequestParam(required = false) String pair, @RequestParam(required = false, defaultValue = "100") Integer limit,
                                                      @RequestParam(required = false,  defaultValue = "0") Integer page) {
        List<TradeRepDto> dto = tradeService.getOrdersByParams(pair, limit, page);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ok", "success", dto));
    }
}
