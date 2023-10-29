package com.example.TradeInterview.dto;

import com.example.TradeInterview.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import java.util.Queue;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BucketDto {
    BigDecimal price = BigDecimal.ZERO;
    Queue<Order> orders;
    BucketDto nextBucket;
    BucketDto prevBucket;
}
