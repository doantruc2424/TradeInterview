package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.entity.ReferencePriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferencePriceRepository extends JpaRepository<ReferencePrice, ReferencePriceId> {

}
