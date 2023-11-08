package com.wex.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity(name = "TAB_PURCHASE_TRANSACTION")
@NoArgsConstructor
public class PurchaseTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String description;
    @Column(nullable = false)
    private LocalDateTime transactionDate;
    @Column(nullable = false, precision=10, scale=2)
    private Double purchaseAmount;


    public PurchaseTransaction(String description, Double purchaseAmount,LocalDateTime transactionDate ){
        this.description = description;
        this.purchaseAmount = purchaseAmount;
        this.transactionDate = transactionDate;
    }
}
