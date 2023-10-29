package com.example.TradeInterview.cronjob.externalSource;

import com.example.TradeInterview.dto.BinanceStickerDto;
import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.entity.id.ReferencePriceId;
import com.example.TradeInterview.repository.ReferencePriceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 1. Price aggregation from the source below:
 * Binance
 * Url : https://api.binance.com/api/v3/ticker/bookTicker
 */
@Service
public class ExternalBinancePrice implements ExternalMarketPrice {
    private static final Logger logger = LoggerFactory.getLogger(ExternalBinancePrice.class);

    @Autowired
    ReferencePriceRepository referencePriceRepository;

    @Override
    public void updateExternalPrice(String source, String url, HashSet<String> pairs, Long updateTime) {
        logger.info("start check price" + Thread.currentThread().getName());

        try {
            String result = makeAPICall(url);
            ObjectMapper mapper = new ObjectMapper();
            List<BinanceStickerDto> stickers = Arrays.asList(mapper.readValue(result, BinanceStickerDto[].class));
            for (BinanceStickerDto sticker : stickers) {
                String symbol = sticker.getSymbol();
                if (pairs.contains(symbol)) {
                    ReferencePrice referencePrice;
                    var referencePriceOptional = referencePriceRepository.findById(new ReferencePriceId(source, symbol));
                    if (referencePriceOptional.isPresent()) {
                        referencePrice = referencePriceOptional.get();
                    } else {
                        referencePrice = new ReferencePrice(source, symbol);

                    }
                    referencePrice.setBidPrice(new BigDecimal(sticker.getBidPrice()));
                    referencePrice.setAskPrice(new BigDecimal(sticker.getAskPrice()));
                    referencePrice.setUpdatedAt(updateTime);
                    logger.info("updateExternalPrice Binance source :" + referencePrice.toString());
                    referencePriceRepository.save(referencePrice);
                    pairs.remove(symbol);
                    if (pairs.isEmpty()) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.info("Error: can not access content - " + e.toString());
        } catch (URISyntaxException e) {
            logger.info("Error: Invalid URL " + e.toString());
        }
        logger.info("start check price" + Thread.currentThread().getName());
    }

    public String makeAPICall(String url) throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(url);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        CloseableHttpResponse response = client.execute(request);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return response_content;
    }

}
