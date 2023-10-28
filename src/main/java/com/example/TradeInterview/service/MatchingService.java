package com.example.TradeInterview.service;

import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.entity.Order;
import com.example.TradeInterview.matchingCore.MatchingEngine;
import com.example.TradeInterview.repository.OrderRepository;
import com.example.TradeInterview.repository.TradeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MatchingService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    OrderRepository orderRepository;

    Map<String, MatchingEngine> matchingEngineMap = new HashMap<>();

    public void createOrder(OrderDto orderDto) {
        MatchingEngine engine = matchingEngineMap.get(orderDto.getPair());
        if (engine == null) {
            engine = new MatchingEngine(tradeRepository, orderRepository, orderDto.getPair());
        }
        engine.matchingOrder(modelMapper.map(orderDto, Order.class));
    }
}
