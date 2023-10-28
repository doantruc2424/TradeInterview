package com.example.TradeInterview.service;

import com.example.TradeInterview.Exception.BalanceNotEnoughException;
import com.example.TradeInterview.Exception.WalletNotFoundException;
import com.example.TradeInterview.containt.OrderStatus;
import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.dto.OrderRepDto;
import com.example.TradeInterview.dto.PairDto;
import com.example.TradeInterview.dto.TradeRepDto;
import com.example.TradeInterview.entity.Order;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.entity.WalletId;
import com.example.TradeInterview.matchingCore.MatchingEngine;
import com.example.TradeInterview.repository.OrderRepository;
import com.example.TradeInterview.repository.TradeRepository;
import com.example.TradeInterview.repository.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    ModelMapper modelMapper;

    Map<String, MatchingEngine> matchingEngineMap = new HashMap<>();
    Map<String, PairDto> currenciesMap = new HashMap<>();

    /**
     *2. User able to see the list of trading transactions
     * @param pair
     * @param limit
     * @param page
     * @return
     */
    public List<TradeRepDto> getOrdersByParams(String pair, Integer limit, Integer page) {
        List<Trade> entities = tradeRepository.findAll();
        return parseModel(entities);
    }

    private List<TradeRepDto> parseModel(List<Trade> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, TradeRepDto.class)).toList();
    }
}
