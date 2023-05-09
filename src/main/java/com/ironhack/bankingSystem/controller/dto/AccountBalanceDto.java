package com.ironhack.bankingSystem.controller.dto;

import com.ironhack.bankingSystem.classes.Money;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceDto {

    @Embedded
    private Money balance;
}
