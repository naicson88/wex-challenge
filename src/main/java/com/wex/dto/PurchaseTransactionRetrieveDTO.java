package com.wex.dto;

import com.wex.entity.PurchaseTransaction;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseTransactionRetrieveDTO extends PurchaseTransaction {
    private Double exchangeRate;
    private Double convertedAmount;
}
