package com.ironhack.bankingSystem.classes;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ThirdPartyTransferRequest {
    @NotNull(message = "Amount can't be null")
    @Positive(message = "Amount can't be negative")
    private BigDecimal amount;
    @NotNull(message = "Account ID can't be null")
    private Integer accountId;
    @NotEmpty(message = "Secret Key can't be empty")
    private String secretKey;
}


