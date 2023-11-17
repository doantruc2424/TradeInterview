package com.example.TradeInterview.service;

import com.example.TradeInterview.Exception.BalanceNotEnoughException;
import com.example.TradeInterview.Exception.SymbolNotSupportException;
import com.example.TradeInterview.Exception.WalletNotFoundException;
import com.example.TradeInterview.config.LoadConfig;
import com.example.TradeInterview.containt.OrderStatus;
import com.example.TradeInterview.dto.AggregationOrderDto;
import com.example.TradeInterview.dto.OrderDto;
import com.example.TradeInterview.payload.OrderRepDto;
import com.example.TradeInterview.dto.PairDto;
import com.example.TradeInterview.entity.Order;
import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.entity.id.WalletId;
import com.example.TradeInterview.matchingCore.MatchingEngine;
import com.example.TradeInterview.repository.OrderRepository;
import com.example.TradeInterview.repository.TradeRepository;
import com.example.TradeInterview.repository.WalletRepository;
import com.example.TradeInterview.util.WalletLock;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    LoadConfig loadConfig;

    Map<String, MatchingEngine> matchingEngineMap = new HashMap<>();

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

    public Long createOrder(AggregationOrderDto orderDto) throws WalletNotFoundException, BalanceNotEnoughException {
        OrderDto order = modelMapper.map(orderDto, OrderDto.class);
        return createOrder(order);
    }

    /**
     * 1. User able to buy/sell the supported crypto trading pairs
     * @param orderDto
     */
    public Long createOrder(OrderDto orderDto) throws WalletNotFoundException, BalanceNotEnoughException {
        MatchingEngine engine = getMatchingEngines().get(orderDto.getPair());
        if (engine == null) {
            throw new SymbolNotSupportException("Coin pair not support");
        }
        Order order = modelMapper.map(orderDto, Order.class);
        order.setRemain(order.getAmount());
        createOrderAndUpdateWallet(order);
        engine.matchingOrder(order);
        return order.getId();
    }

    private void createOrderAndUpdateWallet(Order order) throws WalletNotFoundException, BalanceNotEnoughException {
        updateWalletBalance(order);
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);
    }

    private void updateWalletBalance(Order order) throws WalletNotFoundException, BalanceNotEnoughException {
        String currency = getCurrency(order.getIsBid(), order.getPair().name());
        String businessId = order.getUserId() + currency;
        WalletLock lock = WalletLock.getLockObjectForBusinessId(businessId);
        synchronized (lock) {
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
                throw new BalanceNotEnoughException("Balance not enough");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
            walletRepository.save(wallet);
            WalletLock.releaseLockForBusinessId(businessId);
        }
    }


    private String getCurrency(Boolean isBid, String pair) {
        PairDto currencyDto = loadConfig.getCurrenciesMap().get(pair);
        if(isBid) {
            return currencyDto.getQuote();
        } else {
            return currencyDto.getBase();
        }
    }

    private Map<String, MatchingEngine> getMatchingEngines() {
        if (matchingEngineMap.isEmpty()) {
            Map<String, PairDto> currenciesMap = loadConfig.getCurrenciesMap();
            for(String pair : currenciesMap.keySet()) {
                MatchingEngine engine = new MatchingEngine(tradeRepository, orderRepository, walletRepository, loadConfig, pair);
                matchingEngineMap.put(pair, engine);
            }
        }
        return matchingEngineMap;
    }

    private List<OrderRepDto> parseModel(List<Order> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, OrderRepDto.class)).toList();
    }
}
