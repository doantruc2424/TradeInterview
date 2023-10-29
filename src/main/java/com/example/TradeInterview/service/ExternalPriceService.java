package com.example.TradeInterview.service;


import com.example.TradeInterview.dto.BestPriceDto;
import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.repository.ReferencePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
            List<ReferencePrice> entities = repository.findByPair(pair,Sort.by(Sort.Direction.DESC, "updatedAt"));
            if (entities.isEmpty()) {
                return new ArrayList<>();
            }
            result.add(findBestPriceWithPair(entities));
        } else {
            List<ReferencePrice> entities = repository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
            if (entities.isEmpty()) {
                return new ArrayList<>();
            }
            Map<String, List<ReferencePrice>> groupRefs = new HashMap<>();
            List<ReferencePrice> checkList = new ArrayList<>();
            for (ReferencePrice referencePrice : entities) {
                List<ReferencePrice> list = groupRefs.get(referencePrice.getPair());
                if(list == null) {
                    list = new ArrayList<>();
                }
                list.add(referencePrice);
                groupRefs.put(referencePrice.getPair(), list);
            }
            for(String key : groupRefs.keySet()) {
                result.add(findBestPriceWithPair(groupRefs.get(key)));
            }
        }
        return result;
    }

    private BestPriceDto findBestPriceWithPair(List<ReferencePrice> entities) {
        BestPriceDto result = new BestPriceDto(entities.get(0).getPair(), entities.get(0).getBidPrice(), entities.get(0).getSource(),
                entities.get(0).getAskPrice(), entities.get(0).getSource(), entities.get(0).getUpdatedAt());
        for(int i = 1; i < entities.size(); i++) {
            if (entities.get(i).getUpdatedAt().compareTo(result.getUpdatedAt()) < 0) {
                break;
            }
            if (entities.get(i).getAskPrice().compareTo(result.getAskPrice()) < 0) {
                result.setAskPrice(entities.get(i).getAskPrice());
                result.setAskSource(entities.get(i).getSource());
            }
            if (entities.get(i).getBidPrice().compareTo(result.getBidPrice()) > 0) {
                result.setBidPrice(entities.get(i).getBidPrice());
                result.setBidSource(entities.get(i).getSource());
            }
        }
        return result;
    }
}
