package com.example.TradeInterview.service;

import com.example.TradeInterview.Exception.BalanceNotEnoughException;
import com.example.TradeInterview.Exception.WalletNotFoundException;
import com.example.TradeInterview.containt.OrderStatus;
import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.dto.OrderRepDto;
import com.example.TradeInterview.dto.PairDto;
import com.example.TradeInterview.entity.Order;
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
@PropertySource("classpath:application.properties")
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Value("${currencies.info}")
    private String currenciesInfo;
    @Value("${default.wallet.balance}")
    private BigDecimal defaultBalance;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    OrderRepository orderRepository;

    Map<String, MatchingEngine> matchingEngineMap = new HashMap<>();
    Map<String, PairDto> currenciesMap = new HashMap<>();

    /**
     *
     * @param pair
     * @param limit
     * @param page
     * @return
     */
    public List<OrderRepDto> getOrdersByParams(String pair, Integer limit, Integer page) {
        List<Order> entities = orderRepository.findAll();
        return parseModel(entities);
    }

    /**
     * 1. User able to buy/sell the supported crypto trading pairs
     * @param orderDto
     */
    public Long createOrder(OrderDto orderDto) throws Exception {
        MatchingEngine engine = getMatchingEngines().get(orderDto.getPair());
        Order order = modelMapper.map(orderDto, Order.class);
        order.setRemain(order.getAmount());
        createOrderAndUpdateWallet(order);
        engine.matchingOrder(order);
        return order.getId();
    }

    @Transactional
    private void createOrderAndUpdateWallet(Order order) throws Exception {
        String currency = getCurrency(order.getIsBid(), order.getPair());
        //todo add transaction lock by userId vs currency
        var walletOptional = walletRepository.findById(new WalletId(order.getUserId(), currency));
        if (!walletOptional.isPresent()) {
            throw new WalletNotFoundException("user wallet not found");
        }
        Wallet wallet = walletOptional.get();
        BigDecimal amount;
        if(order.getIsBid()) {
            amount = order.getAmount().multiply(order.getPrice()).setScale(8, RoundingMode.UP);
        } else {
            amount = order.getAmount();
        }
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new BalanceNotEnoughException();
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        order.setStatus(OrderStatus.NEW.name());
        orderRepository.save(order);
    }

    private String getCurrency(Boolean isBid, String pair) {
        PairDto currencyDto = getCurrenciesMap().get(pair);
        if(isBid) {
            return currencyDto.getQuote();
        } else {
            return currencyDto.getBase();
        }
    }

    private Map<String, PairDto> getCurrenciesMap() {
        if (currenciesMap.isEmpty()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<PairDto> pairs = Arrays.asList(mapper.readValue(currenciesInfo, PairDto[].class));
                for (PairDto dto : pairs) {
                    currenciesMap.put(dto.getSymbol(), dto);
                }
            } catch (Exception e) {
                logger.error("cannot parse currencies.info", e.toString());
            }
        }
        return currenciesMap;
    }

    private Map<String, MatchingEngine> getMatchingEngines() {
        if (matchingEngineMap.isEmpty()) {
            Map<String, PairDto> currenciesMap = getCurrenciesMap();
            for(String pair : currenciesMap.keySet()) {
                MatchingEngine engine = new MatchingEngine(tradeRepository, orderRepository, pair);
                matchingEngineMap.put(pair, engine);
            }
        }
        return matchingEngineMap;
    }

    private List<OrderRepDto> parseModel(List<Order> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, OrderRepDto.class)).toList();
    }
}
