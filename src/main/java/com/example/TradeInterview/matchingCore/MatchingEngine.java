package com.example.TradeInterview.matchingCore;

import com.example.TradeInterview.containt.OrderStatus;
import com.example.TradeInterview.dto.BucketDto;
import com.example.TradeInterview.dto.PairDto;
import com.example.TradeInterview.entity.Order;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.entity.id.WalletId;
import com.example.TradeInterview.repository.OrderRepository;
import com.example.TradeInterview.repository.TradeRepository;
import com.example.TradeInterview.repository.WalletRepository;
import com.example.TradeInterview.config.LoadConfig;
import com.example.TradeInterview.util.WalletLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatchingEngine {
    BucketDto bidBestPrice;
    BucketDto askBestPrice;

    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    LoadConfig loadConfig;

    public MatchingEngine(TradeRepository tradeRepository, OrderRepository orderRepository, WalletRepository walletRepository, LoadConfig loadConfig, String pair) {
        this.tradeRepository = tradeRepository;
        this.orderRepository = orderRepository;
        this.walletRepository = walletRepository;
        this.loadConfig = loadConfig;
        loadData(pair);
    }

    private void loadData(String pair) {
        List<Order> orders = orderRepository.findByPairOrderByUpdatedAt(pair);
        for (Order order : orders) {
            addOrderToBucket(order);
        }
    }

    public void matchingOrder(Order newOrder) {
        synchronized(this) {
            newOrder.setCreatedAt(System.currentTimeMillis());
            newOrder.setUpdatedAt(System.currentTimeMillis());
            newOrder.setStatus(OrderStatus.IN_PROCESS.name());
            orderRepository.save(newOrder);
            BigDecimal remain;
            if (newOrder.getIsBid()) {
                remain = tryMatchingBid(newOrder);
            } else {
                remain = tryMatchingAsk(newOrder);
            }
            if(remain.compareTo(BigDecimal.ZERO) == 0) {
                newOrder.setRemain(remain);
                newOrder.setStatus(OrderStatus.FILLED.name());
                orderRepository.save(newOrder);
                return;
            } if (remain.compareTo(newOrder.getRemain()) < 0) {
                newOrder.setRemain(remain);
                newOrder.setStatus(OrderStatus.PARTIALLY_FILLED.name());
                orderRepository.save(newOrder);
            }
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
            while (prevBucket!= null && newOrder.getPrice().compareTo(prevBucket.getPrice()) < 0) {
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
            BucketDto newBucket = new BucketDto(newOrder.getPrice(), orders, nextBucket, prevBucket);
            if (nextBucket != null) {
                nextBucket.setPrevBucket(newBucket);
            }
            if (prevBucket != null) {
                prevBucket.setNextBucket(newBucket);
            }
        } else {
            existedBucket.getOrders().add(newOrder);
        }
    }



    private void saveTrade(Order newOrder, Order makerOrder, BigDecimal amount) {
        Trade trade = new Trade(
                newOrder.getIsBid() ? newOrder.getId() : makerOrder.getId(),
                newOrder.getIsBid() ? makerOrder.getId() : newOrder.getId(),
                newOrder.getIsBid() ? newOrder.getUserId() : makerOrder.getUserId(),
                newOrder.getIsBid() ? makerOrder.getUserId() : newOrder.getUserId(),
                makerOrder.getPair(), makerOrder.getPrice(), amount, newOrder.getCreatedAt());
        tradeRepository.save(trade);
        addBalance(trade);
    }

    private void addBalance(Trade trade) {
        PairDto pair = loadConfig.getCurrenciesMap().get(trade.getPair());
        addBalance(trade.getUserAsk(), pair.getQuote(), trade.getAmount().multiply(trade.getPrice()).setScale(8, RoundingMode.DOWN));
        addBalance(trade.getUserBid(), pair.getBase(), trade.getAmount());
    }

    private void addBalance(Long user, String currency, BigDecimal amount) {
        String businessId = user + currency;
        WalletLock lock = WalletLock.getLockObjectForBusinessId(businessId);
        synchronized (lock) {
            var walletOptional = walletRepository.findById(new WalletId(user, currency));
            if (walletOptional.isPresent()) {
                Wallet wallet = walletOptional.get();
                wallet.setBalance(wallet.getBalance().add(amount));
                walletRepository.save(wallet);
            }
            WalletLock.releaseLockForBusinessId(businessId);
        }
    }

    private BigDecimal tryMatchingBid(Order newOrder) {
        BigDecimal remain = newOrder.getRemain();
        if (askBestPrice == null) {
            return remain;
        }
        while (askBestPrice.getPrice().compareTo(newOrder.getPrice()) <= 0 && remain.compareTo(BigDecimal.ZERO) > 0) {
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

    private BigDecimal tryMatchingAsk(Order newOrder) {
        BigDecimal remain = newOrder.getRemain();
        if (bidBestPrice == null) {
            return remain;
        }
        while (bidBestPrice.getPrice().compareTo(newOrder.getPrice()) >= 0 && remain.compareTo(BigDecimal.ZERO) > 0) {
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
