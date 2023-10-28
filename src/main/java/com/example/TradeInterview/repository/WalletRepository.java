package com.example.TradeInterview.repository;

import com.example.TradeInterview.entity.Wallet;
import com.example.TradeInterview.entity.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, WalletId> {


}
