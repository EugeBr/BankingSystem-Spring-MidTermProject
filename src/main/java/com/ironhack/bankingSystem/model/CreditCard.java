package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
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
    private final String TYPE = "CREDIT CARD";
    @DecimalMax(value = "100000", message = "Credit limit can't exceed US$ 100000")
    private BigDecimal creditLimit = new BigDecimal(100);
    @DecimalMin(value = "0.1", message = "Interest rate can't be lower than 0.1")
    private Double interestRate = 0.2;
    @PastOrPresent(message = "Last interest date can't be in the future")
    private LocalDate lastInterestDate = LocalDate.now();

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status) {
        super(balance, primaryOwner, createdBy, status);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, BigDecimal creditLimit) {
        super(balance, primaryOwner, createdBy, status);
        this.creditLimit = creditLimit;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, Double interestRate) {
        super(balance, primaryOwner, createdBy, status);
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, BigDecimal creditLimit, Double interestRate) {
        super(balance, primaryOwner, createdBy, status);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, BigDecimal creditLimit) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.creditLimit = creditLimit;
    }

    public CreditCard(Double interestRate) {
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, BigDecimal creditLimit, Double interestRate) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    //check if it's time to take interests
    @Override
    public void checkInterest() {
        LocalDate currentDate = LocalDate.now();
        LocalDate currentDateMinus30Days = currentDate.minusDays(30);
        if(lastInterestDate.isBefore(currentDateMinus30Days)) {
            BigDecimal balanceByInterestRate = new BigDecimal(String.valueOf(super.getBalance().getAmount().multiply(new BigDecimal(interestRate))));
            Money newBalance = new Money(super.getBalance().decreaseAmount(balanceByInterestRate));
            super.setBalance(newBalance);
            setLastInterestDate(currentDate);
        }
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "TYPE='" + TYPE + '\'' +
                ", creditLimit=" + creditLimit +
                ", interestRate=" + interestRate +
                ", lastInterestDate=" + lastInterestDate +
                "} " + super.toString();
    }
}
