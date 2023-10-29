package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.entity.id.ReferencePriceId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferencePriceRepository extends JpaRepository<ReferencePrice, ReferencePriceId> {
    List<ReferencePrice> findByPair(String pair, Sort sort);
}
