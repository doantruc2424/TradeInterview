package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT t.userId, t.pair, t.isBid, t.price, t.amount, t.createdAt, t.updatedAt FROM Order t where t.pair = :pair ORDER BY t.createdAt ASC")
    List<Order> findByPairOrderByUpdatedAt(@Param("pair") String Pair);
}
