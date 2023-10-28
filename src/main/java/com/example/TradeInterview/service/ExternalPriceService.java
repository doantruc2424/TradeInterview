package com.example.TradeInterview.service;


import com.example.TradeInterview.dto.BestPriceDto;
import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.repository.ReferencePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ExternalPriceService {

    @Autowired
    ReferencePriceRepository repository;

    public List<BestPriceDto> findBestPrice(String pair) {
        List<BestPriceDto> result = new ArrayList<>();
        if (StringUtils.hasText(pair)) {
            List<ReferencePrice> entities = repository.findByPairOrderByUpdatedAt(pair);
            if (entities.isEmpty()) {
                return new ArrayList<>();
            }
            result.add(findBestPriceWithPair(pair, entities));
        } else {
            List<ReferencePrice> entities = repository.findAllOrderByPairVsUpdatedAt();
            if (entities.isEmpty()) {
                return new ArrayList<>();
            }
            List<ReferencePrice> checkList = null;
            String symbol = entities.get(0).getPair();
            for (ReferencePrice referencePrice : entities) {
                if (!symbol.equals(referencePrice.getPair())) {
                    result.add(findBestPriceWithPair(pair, checkList));
                    checkList = new ArrayList<>();
                }
                checkList.add(referencePrice);
            }
            if (checkList != null || !checkList.isEmpty()) {
                result.add(findBestPriceWithPair(pair, checkList));
            }
        }
        return result;
    }

    private BestPriceDto findBestPriceWithPair(String pair, List<ReferencePrice> entities) {
        BestPriceDto result = new BestPriceDto(pair, entities.get(0).getBidPrice(), entities.get(0).getSource(),
                entities.get(0).getAskPrice(), entities.get(0).getSource(), entities.get(0).getUpdatedAt());
        for(int i = 1; i < entities.size(); i++) {
            if (entities.get(i).getUpdatedAt().compareTo(result.getUpdatedAt()) < 0) {
                break;
            }
            if (entities.get(i).getAskPrice().compareTo(result.getAskPrice()) < 0) {
                result.setAskPrice(entities.get(i).getAskPrice());
                result.setAskSource(entities.get(i).getSource());
            }
            if (entities.get(i).getAskPrice().compareTo(result.getAskPrice()) > 0) {
                result.setBidPrice(entities.get(i).getBidPrice());
                result.setBidSource(entities.get(i).getSource());
            }
        }
        return result;
    }
}
