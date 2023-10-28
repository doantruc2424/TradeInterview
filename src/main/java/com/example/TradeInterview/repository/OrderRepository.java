package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.Order;
import com.example.TradeInterview.entity.OrderId;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.entity.TradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderId> {

}