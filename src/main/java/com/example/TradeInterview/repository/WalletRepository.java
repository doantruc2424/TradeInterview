package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.entity.id.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, WalletId> {

    List<Wallet> findByUserId(Long UserId);

    @Modifying
    @Query("update Wallet t set t.balance = (t.balance + :amount) "
            + "where t.userId = :userId and t.currency = :currency ")
    void addBalance(@Param("userId") Long user, @Param("currency") String currency, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("update Wallet t set t.balance = (t.balance - :amount) "
            + "where t.userId = :userId and t.currency = :currency ")
    void subtractBalance(@Param("userId") Long user, @Param("currency") String currency, @Param("amount") BigDecimal amount);
}
