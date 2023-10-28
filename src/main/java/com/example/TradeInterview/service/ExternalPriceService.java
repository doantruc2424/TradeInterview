package com.example.TradeInterview.service;

import com.example.TradeInterview.cronjob.UpdatePriceExternalJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class ExternalPriceService implements ExternalMarketPrice {

    private static final Logger logger = LoggerFactory.getLogger(ExternalPriceService.class);

    private ExternalMarketPrice priceService;


    public ExternalPriceService(String source) {
        if(source.equals("binance")) {
            this.priceService = new ExternalBinancePrice();
        } else {
            logger.error("External Price source not support" + source);
        }
    }
    @Override
    public void updateExternalPrice(String source, String url, HashSet<String> pairs) {
        logger.error("Update latest price of " + source);
        this.priceService.updateExternalPrice(source, url, pairs);
    }
}
