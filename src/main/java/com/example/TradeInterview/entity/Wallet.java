package com.example.TradeInterview.entity;

import com.example.TradeInterview.entity.id.WalletId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Entity
@Table(name = "trade_wallet")
@IdClass(WalletId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Id
    @Column(name = "currency", length = 10)
    private String currency;
    @Check(constraints = "balance >= 0")
    @Column(name = "balance", precision = 8)
    private BigDecimal balance;
}
