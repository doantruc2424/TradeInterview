package com.example.TradeInterview.service;

import java.util.HashSet;

public interface ExternalMarketPrice {

    void updateExternalPrice(String source, String url, HashSet<String> pairs);
}
