package com.ironhack.bankingSystem.classes;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "Account ID can't be null")
    private Integer accountId;
    @NotNull(message = "Recipient account ID can't be null")
    private Integer recipientAccountId;
    @NotNull(message = "Amount can't be null")
    @Positive(message = "Amount can't be negative")
    private BigDecimal amount;
}
