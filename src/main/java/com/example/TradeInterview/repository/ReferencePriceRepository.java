package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.ReferencePrice;
import com.example.TradeInterview.entity.ReferencePriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferencePriceRepository extends JpaRepository<ReferencePrice, ReferencePriceId> {

    @Query("SELECT t.pair, t.source, t.bidPrice, t.askPrice, t.updatedAt "
            + "FROM ReferencePrice t where t.pair = :pair ORDER BY t.updatedAt DESC")
    List<ReferencePrice> findByPairOrderByUpdatedAt(@Param("pair") String Pair);
    @Query("SELECT t.pair, t.source, t.bidPrice, t.askPrice, t.updatedAt "
            + "FROM ReferencePrice t where ORDER BY t.pair, t.updatedAt DESC")
    List<ReferencePrice> findAllOrderByPairVsUpdatedAt();
}
