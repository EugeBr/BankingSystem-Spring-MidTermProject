package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Account{
    @DecimalMax("100000")
    private BigDecimal creditLimit = new BigDecimal(100);
    @DecimalMin("0.1")
    private Double interestRate = 0.2;
    @PastOrPresent
    private LocalDate lastInterestDate = LocalDate.now();

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy) {
        super(balance, primaryOwner, createdBy);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, BigDecimal creditLimit) {
        super(balance, primaryOwner, createdBy);
        this.creditLimit = creditLimit;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, Double interestRate) {
        super(balance, primaryOwner, createdBy);
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, BigDecimal creditLimit, Double interestRate) {
        super(balance, primaryOwner, createdBy);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, BigDecimal creditLimit) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.creditLimit = creditLimit;
    }

    public CreditCard(Double interestRate) {
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, BigDecimal creditLimit, Double interestRate) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditLimit=" + creditLimit +
                ", interestRate=" + interestRate +
                ", lastInterestDate=" + lastInterestDate +
                "} " + super.toString();
    }
}
