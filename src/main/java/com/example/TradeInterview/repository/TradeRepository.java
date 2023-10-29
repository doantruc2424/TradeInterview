package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.entity.Trade;
import com.example.TradeInterview.entity.id.TradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, TradeId> {

}
