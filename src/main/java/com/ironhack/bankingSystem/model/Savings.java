package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.*;
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
public class Savings extends Account{
    private final String TYPE = "SAVINGS ACCOUNT";
    @NotEmpty(message = "SecretKey can't be empty")
    private String secretKey;
    @DecimalMin(value = "100", message = "Minimum balance can't be lower than US$ 1000")
    private BigDecimal minimumBalance = new BigDecimal(1000);
    @DecimalMax(value = "0.5", message = "Interest rate can't exceed 0.5")
    private Double interestRate = 0.0025;
    @PastOrPresent(message = "Last interest date can't be in the future")
    private LocalDate lastInterestDate = LocalDate.now();

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, String secretKey) {
        super(balance, primaryOwner, createdBy, status);
        this.secretKey = secretKey;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, String secretKey, BigDecimal minimumBalance) {
        super(balance, primaryOwner, createdBy, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, String secretKey, Double interestRate) {
        super(balance, primaryOwner, createdBy, status);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, String secretKey, BigDecimal minimumBalance, Double interestRate) {
        super(balance, primaryOwner, createdBy, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.secretKey = secretKey;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, String secretKey, BigDecimal minimumBalance) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, String secretKey, Double interestRate) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, String secretKey, BigDecimal minimumBalance, Double interestRate) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    //check if balance is lower that minimum balance
    @Override
    public void checkMinimumBalance() {
        if(getMinimumBalance().compareTo(getBalance().getAmount()) == 1) {
            super.applyPenaltyFee();
        }
    }

    //check if it's time to add interests
    @Override
    public void checkInterest() {
        LocalDate currentDate = LocalDate.now();
        LocalDate currentDateMinus1Year = currentDate.minusYears(1);
        if(lastInterestDate.isBefore(currentDateMinus1Year)) {
            BigDecimal balanceByInterestRate = new BigDecimal(String.valueOf(super.getBalance().getAmount().multiply(new BigDecimal(interestRate))));
            Money newBalance = new Money(super.getBalance().increaseAmount(balanceByInterestRate));
            super.setBalance(newBalance);
            setLastInterestDate(currentDate);
        }
    }

    @Override
    public String toString() {
        return "Savings{" +
                "TYPE='" + TYPE + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", minimumBalance=" + minimumBalance +
                ", interestRate=" + interestRate +
                ", lastInterestDate=" + lastInterestDate +
                "} " + super.toString();
    }
}
