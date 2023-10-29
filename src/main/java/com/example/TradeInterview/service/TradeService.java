package com.example.TradeInterview.service;

import com.example.TradeInterview.dto.PairDto;
import com.example.TradeInterview.payload.TradeRepDto;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.matchingCore.MatchingEngine;
import com.example.TradeInterview.repository.TradeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
