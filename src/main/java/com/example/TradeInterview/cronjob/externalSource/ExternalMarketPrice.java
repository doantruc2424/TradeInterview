package com.example.TradeInterview.cronjob.externalSource;

import java.util.HashSet;

public interface ExternalMarketPrice {

    void updateExternalPrice(String source, String url, HashSet<String> pairs, Long updateTime);
}
