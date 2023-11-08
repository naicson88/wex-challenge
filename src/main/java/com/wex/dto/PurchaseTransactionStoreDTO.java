package com.wex.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PurchaseTransactionStoreDTO {
    @Size(max = 50, message = "Description length must be max 50 characters")
    private String description;
    @DecimalMin("0.0")
    @NotNull
    private Double purchaseAmount;
}
