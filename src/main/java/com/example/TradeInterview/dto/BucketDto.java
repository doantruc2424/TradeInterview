package com.example.TradeInterview.dto;

import com.example.TradeInterview.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import java.util.Queue;

@Getter
@Setter
public class BucketDto {
    BigDecimal price = BigDecimal.ZERO;
    Queue<Order> orders;
    BucketDto nextBucket;
    BucketDto prevBucket;

    public BucketDto() {

    }
    public BucketDto(BigDecimal price, Queue<Order> orders, BucketDto nextBucket, BucketDto prevBucket) {
        this.price = price;
        this.orders = orders;
        this.nextBucket = nextBucket;
        this.prevBucket = prevBucket;
    }
}
