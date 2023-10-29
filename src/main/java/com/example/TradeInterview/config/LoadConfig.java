package com.example.TradeInterview.config;

import com.example.TradeInterview.dto.PairDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class LoadConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoadConfig.class);
    private Map<String, PairDto> currenciesMap = new HashMap<>();
    @Value("${currencies.info}")
    private String currenciesInfo;
    @Value("${default.wallet.balance}")
    private BigDecimal defaultBalance;
    @Value("${list.url}")
    private String url;
    @Value("${update.price.external.turn.on}")
    private boolean jobTurnOn;


    public String getUrl() {
        return url;
    }

    public boolean isJobTurnOn() {
        return jobTurnOn;
    }

    public BigDecimal getDefaultBalance(){
        return defaultBalance;
    }

    public Map<String, PairDto> getCurrenciesMap() {
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
}
