package com.example.TradeInterview.service;

import com.example.TradeInterview.Exception.BalanceNotEnoughException;
import com.example.TradeInterview.Exception.WalletNotFoundException;
import com.example.TradeInterview.containt.OrderStatus;
import com.example.TradeInterview.dto.OrderDto;
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
public class MatchingService {
    private static final Logger logger = LoggerFactory.getLogger(MatchingService.class);
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

    public MatchingService() {
    }
    public void createOrder(OrderDto orderDto) {
        MatchingEngine engine = getMatchingEngines().get(orderDto.getPair());
        Order order = modelMapper.map(orderDto, Order.class);
        order.setRemain(order.getAmount());
        engine.matchingOrder(order);
    }

    @Transactional
    private void createOrderAndUpdateWallet(Order order) {
        String currency = getCurrency(order.getIsBid(), order.getPair());
        //todo add transaction lock by userId vs currency
        var walletOptional = walletRepository.findById(new WalletId(order.getUserId(), currency));
        if (!walletOptional.isPresent()) {
            throw new WalletNotFoundException();
        }
        Wallet wallet = walletOptional.get();
        BigDecimal amount = BigDecimal.ZERO;
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
}
