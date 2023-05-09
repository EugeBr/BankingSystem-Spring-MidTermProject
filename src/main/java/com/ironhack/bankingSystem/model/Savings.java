package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
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
public class Savings extends Account{
    private final String TYPE = "SAVINGS ACCOUNT";
    @NotEmpty
    private String secretKey;
    @DecimalMin("100")
    private BigDecimal minimumBalance = new BigDecimal(1000);
    @Enumerated(EnumType.STRING)
    private Status status;
    @DecimalMax("0.5")
    private Double interestRate = 0.0025;
    @PastOrPresent
    private LocalDate lastInterestDate = LocalDate.now();

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, String secretKey, Status status) {
        super(balance, primaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, String secretKey, BigDecimal minimumBalance, Status status) {
        super(balance, primaryOwner, createdBy);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.status = status;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, String secretKey, Status status, Double interestRate) {
        super(balance, primaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
        this.interestRate = interestRate;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Admin createdBy, String secretKey, BigDecimal minimumBalance, Status status, Double interestRate) {
        super(balance, primaryOwner, createdBy);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.status = status;
        this.interestRate = interestRate;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, String secretKey, BigDecimal minimumBalance, Status status) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.status = status;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, String secretKey, Status status, Double interestRate) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
        this.interestRate = interestRate;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, String secretKey, BigDecimal minimumBalance, Status status, Double interestRate) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.status = status;
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
                "secretKey='" + secretKey + '\'' +
                ", minimumBalance=" + minimumBalance +
                ", status=" + status +
                ", interestRate=" + interestRate +
                ", lastInterestDate=" + lastInterestDate +
                "} " + super.toString();
    }
}
