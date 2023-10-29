package com.example.TradeInterview.entity;


import com.example.TradeInterview.entity.id.ReferencePriceId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "reference_price")
@IdClass(ReferencePriceId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferencePrice {
    @Id
    private String source;
    @Id
    private String pair;
    @Column(precision = 32, scale = 6)
    private BigDecimal bidPrice;
    @Column(precision = 32, scale = 6)
    private BigDecimal askPrice;
    private Long updatedAt;



    public ReferencePrice(String source, String pair) {
        this.source = source;
        this.pair = pair;
    }
}
