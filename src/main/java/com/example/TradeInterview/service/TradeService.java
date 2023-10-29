package com.example.TradeInterview.service;

import com.example.TradeInterview.payload.TradeRepDto;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);
    @Autowired
    TradeRepository tradeRepository;

    /**
     *2. User able to see the list of trading transactions
     * @param pair
     * @param limit
     * @param page
     * @return
     */
    public List<TradeRepDto> getOrdersByParams(Long userId, String pair, Integer limit, Integer page) {
        List<Trade> entities = tradeRepository.findByUserAskOrUserBid(userId, userId, Sort.by(Sort.Direction.DESC, "createdAt"));
        return parseManual(entities, userId);
    }

    private List<TradeRepDto> parseManual(List<Trade> entities, Long userId) {
        List<TradeRepDto> result = new ArrayList<>();
        for(Trade trade : entities) {
            result.add(new TradeRepDto(
                    trade.getUserBid().compareTo(userId) == 0 ? trade.getOrderBid() : trade.getOrderAsk(),
                    trade.getPair(),
                    trade.getPrice(),
                    trade.getAmount(),
                    trade.getUserBid().compareTo(userId) == 0 ? Boolean.TRUE : Boolean.FALSE,
                    trade.getCreatedAt()));
        }
        return result;
    }
}
