package com.example.TradeInterview.dto;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Collection;
import java.util.List;

public class BinanceStickersReturnDto {
    List<Object> data;

    LinkedTreeMap status;

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public LinkedTreeMap getStatus() {
        return status;
    }

    public void setStatus(LinkedTreeMap status) {
        this.status = status;
    }
}
