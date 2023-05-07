package com.ironhack.bankingSystem.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Account{
    private BigDecimal creditLimit;
    private Double interestRate;            //? final?
}
