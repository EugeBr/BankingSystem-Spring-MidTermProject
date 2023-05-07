package com.ironhack.bankingSystem.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Account{
    private BigDecimal creditLimit;
    private Double interestRate;            //? final?
    @Value("LocalDate.now()")               //? default?
    private LocalDate lastInterestDate = LocalDate.now();
}
