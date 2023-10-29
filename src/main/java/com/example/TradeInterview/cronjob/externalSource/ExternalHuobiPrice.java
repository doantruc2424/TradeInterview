package com.example.TradeInterview.cronjob.externalSource;

import com.example.TradeInterview.dto.HuobiReturnDataDto;
import com.example.TradeInterview.dto.HuobiStickerDto;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

/**
 * 1. Price aggregation from the source below:
 * Houbi
 * Url : https://api.huobi.pro/market/tickers
 */
@Service
public class ExternalHuobiPrice implements ExternalMarketPrice {
    private static final Logger logger = LoggerFactory.getLogger(ExternalHuobiPrice.class);

    @Autowired
    ReferencePriceRepository referencePriceRepository;

    @Override
    public void updateExternalPrice(String source, String url, HashSet<String> pairs, Long updateTime) {
        logger.info("start check price" + Thread.currentThread().getName());
        try {
            String result = makeAPICall(url);
            ObjectMapper mapper = new ObjectMapper();
            HuobiReturnDataDto HuobiData = mapper.readValue(result, HuobiReturnDataDto.class);

            List<HuobiStickerDto> stickers = HuobiData.getData();
            for (HuobiStickerDto sticker : stickers) {
                String symbol = sticker.getSymbol();
                if (pairs.contains(symbol)) {
                    symbol = symbol.toUpperCase();
                    ReferencePrice referencePrice;
                    var referencePriceOptional = referencePriceRepository.findById(new ReferencePriceId(source, symbol));
                    if (referencePriceOptional.isPresent()) {
                        referencePrice = referencePriceOptional.get();
                    } else {
                        referencePrice = new ReferencePrice(source, symbol);

                    }
                    referencePrice.setBidPrice(new BigDecimal(sticker.getBid()));
                    referencePrice.setAskPrice(new BigDecimal(sticker.getAsk()));
                    referencePrice.setUpdatedAt(updateTime);
                    logger.info("updateExternalPrice Huobi source :" + referencePrice.toString());

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
