package com.ironhack.bankingSystem.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferRequest {
    private Integer accountId;
    private Integer recipientAccountId;
    private BigDecimal amount;
}
