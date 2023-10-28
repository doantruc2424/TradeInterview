package com.example.TradeInterview.matchingCore;

import com.example.TradeInterview.dto.BucketDto;
import com.example.TradeInterview.entity.Order;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.repository.OrderRepository;
import com.example.TradeInterview.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatchingEngine {
    BucketDto bidBestPrice = new BucketDto();
    BucketDto askBestPrice = new BucketDto();

    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    OrderRepository orderRepository;

    public MatchingEngine(TradeRepository tradeRepository, OrderRepository orderRepository, String pair) {
        this.tradeRepository = tradeRepository;
        this.orderRepository = orderRepository;
        loadData(pair);
    }

    private void loadData(String pair) {
        List<Order> orders = orderRepository.findByPairOrderByUpdatedAt(pair);
        for (Order order : orders) {
            addOrderToBucket(order);
        }
    }

    public synchronized void matchingOrder(Order newOrder) {
        newOrder.setCreatedAt(System.currentTimeMillis());
        newOrder.setUpdatedAt(System.currentTimeMillis());
        orderRepository.save(newOrder);
        BigDecimal remain;
        if (newOrder.getIsBid()) {
            remain = tryMatchingBid(newOrder);
        } else {
            remain = tryMatchingAsk(newOrder);
        }
        if(remain.compareTo(BigDecimal.ZERO) > 0) {
            newOrder.setRemain(remain);
            orderRepository.save(newOrder);
            addOrderToBucket(newOrder);
        }

    }

    private void addOrderToBucket(Order newOrder) {
        BucketDto prevBucket;
        BucketDto nextBucket = null;
        if(newOrder.getIsBid()) {
            if (bidBestPrice == null) {
                Queue<Order> orders = new LinkedList<>();
                orders.add(newOrder);
                bidBestPrice = new BucketDto(newOrder.getPrice(), orders, null, null);
                return;
            }
            prevBucket = bidBestPrice;
            while (newOrder.getPrice().compareTo(prevBucket.getPrice()) < 0) {
                nextBucket = prevBucket;
                prevBucket = prevBucket.getPrevBucket();
            }
        } else {
            if (askBestPrice == null) {
                Queue<Order> orders = new LinkedList<>();
                orders.add(newOrder);
                askBestPrice = new BucketDto(newOrder.getPrice(), orders, null, null);
                return;
            }
            prevBucket = askBestPrice;
            while (newOrder.getPrice().compareTo(prevBucket.getPrice()) > 0) {
                nextBucket = prevBucket;
                prevBucket = prevBucket.getPrevBucket();
            }
        }
        BucketDto existedBucket = null;
        if(nextBucket != null && newOrder.getPrice().compareTo(nextBucket.getPrice()) == 0) {
            existedBucket = nextBucket;
        }
        if(prevBucket != null && newOrder.getPrice().compareTo(prevBucket.getPrice()) == 0) {
            existedBucket = prevBucket;
        }
        if(existedBucket == null) {
            Queue<Order> orders = new LinkedList<>();
            orders.add(newOrder);
            new BucketDto(newOrder.getPrice(), orders, nextBucket, prevBucket);
        } else {
            existedBucket.getOrders().add(newOrder);
        }
    }

    private BigDecimal tryMatchingBid(Order newOrder) {
        BigDecimal remain = newOrder.getRemain();
        if (askBestPrice == null) {
            return remain;
        }
        while (askBestPrice.getPrice().compareTo(newOrder.getPrice()) <= 0 || remain.compareTo(BigDecimal.ZERO) > 0) {
            Order order = askBestPrice.getOrders().peek();
            if (order == null) {
                askBestPrice = askBestPrice.getPrevBucket();
                if (askBestPrice == null) {
                    break;
                }
            }
            if (order.getRemain().compareTo(remain) == 0) {
                saveTrade(newOrder, order, remain);
                remain = BigDecimal.ZERO;
                askBestPrice.getOrders().remove(order);
            } else if (order.getRemain().compareTo(remain) < 0) {
                saveTrade(newOrder, order, order.getRemain());
                remain = remain.subtract(order.getRemain());
                askBestPrice.getOrders().remove(order);
            } else {
                saveTrade(newOrder, order, remain);
                order.setRemain(order.getRemain().subtract(remain));
                remain = BigDecimal.ZERO;
            }
        }
        return remain;
    }

    private void saveTrade(Order newOrder, Order makerOrder, BigDecimal amount) {
        tradeRepository.save(new Trade(newOrder.getIsBid() ? newOrder.getUserId() : makerOrder.getUserId(),
                newOrder.getIsBid() ? makerOrder.getUserId() : newOrder.getUserId(), makerOrder.getPair(),
                makerOrder.getPrice(), amount, newOrder.getCreatedAt()));
    }

    private BigDecimal tryMatchingAsk(Order newOrder) {
        BigDecimal remain = newOrder.getRemain();
        if (bidBestPrice == null) {
            return remain;
        }
        while (bidBestPrice.getPrice().compareTo(newOrder.getPrice()) >= 0 || remain.compareTo(BigDecimal.ZERO) > 0) {
            Order order = bidBestPrice.getOrders().peek();
            if (order == null) {
                bidBestPrice = bidBestPrice.getPrevBucket();
                if (bidBestPrice == null) {
                    break;
                }
            }
            if (order.getRemain().compareTo(remain) == 0) {
                saveTrade(newOrder, order, remain);
                remain = BigDecimal.ZERO;
                bidBestPrice.getOrders().remove(order);
            } else if (order.getRemain().compareTo(remain) < 0) {
                saveTrade(newOrder, order, order.getRemain());
                remain = remain.subtract(order.getRemain());
                bidBestPrice.getOrders().remove(order);
            } else {
                saveTrade(newOrder, order, remain);
                order.setRemain(order.getRemain().subtract(remain));
                remain = BigDecimal.ZERO;
            }
        }
        return remain;
    }
}
