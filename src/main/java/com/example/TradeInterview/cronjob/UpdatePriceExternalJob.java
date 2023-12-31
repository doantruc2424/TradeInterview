package com.example.TradeInterview.cronjob;

import com.example.TradeInterview.cronjob.externalSource.ExternalBinancePrice;
import com.example.TradeInterview.cronjob.externalSource.ExternalHuobiPrice;
import com.example.TradeInterview.cronjob.externalSource.ExternalMarketPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@EnableScheduling
@PropertySource("classpath:application.properties")
public class UpdatePriceExternalJob {

    private static final Logger logger = LoggerFactory.getLogger(UpdatePriceExternalJob.class);;
    @Value("${list.url}")
    private String url;
    @Value("${update.price.external.turn.on}")
    private boolean jobTurnOn;
    @Autowired
    private ExternalHuobiPrice huobiPrice;
    @Autowired
    private ExternalBinancePrice binancePrice;

    /**
     * Task
     * 1. Price aggregation from the source below:
     */
    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
        logger.info("start update external price");
        if(jobTurnOn) {
            String[] sources = url.split(" ");
            Long updateTime = System.currentTimeMillis();
            for (String source : sources) {
                String[] sourceInfo = source.split("-");
                ExternalMarketPrice priceService = pickExternalService(sourceInfo[0]);
                HashSet<String> pairs = new HashSet<String>();
                for (String pair: sourceInfo[2].split(",")) {
                    pairs.add(pair);
                }
                new Thread(() -> {
                    priceService.updateExternalPrice(sourceInfo[0], sourceInfo[1], pairs, updateTime);
                }).start();
            }
        }
    }
    private  ExternalMarketPrice pickExternalService(String source) {
        if (source.equals("binance")) {
            return binancePrice;
        } else if(source.equals("huobi")) {
            return huobiPrice;
        } else {
            logger.error("External Price source not support " + source);
            return null;
        }
    }
}
