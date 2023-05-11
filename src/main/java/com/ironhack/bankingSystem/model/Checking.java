package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checking extends Account{
    private final String TYPE = "CHECKING ACCOUNT";
    @NotEmpty(message = "SecretKey can't be empty")
    private String secretKey;
    @Embedded
    private final Money MINIMUM_BALANCE = new Money(new BigDecimal("250.00"));
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_fee_currency"))
    })
    private final Money MONTHLY_MAINTENANCE_FEE = new Money(new BigDecimal("12.0"));
    @PastOrPresent(message = "Date can't be in the future")
    private LocalDate lastMonthlyFeeDate = LocalDate.now();

    public Checking(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, String secretKey) {
        super(balance, primaryOwner, createdBy, status);
        this.secretKey = secretKey;
    }

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.secretKey = secretKey;
    }

    //check if balance is lower that minimum balance
    @Override
    public void checkMinimumBalance() {
        if(getMINIMUM_BALANCE().getAmount().compareTo(getBalance().getAmount()) == 1) {
            super.applyPenaltyFee();
        }
    }

    //check if it's time for monthly fee
    @Override
    public void checkMonthlyFee() {
        LocalDate currentDate = LocalDate.now();
        LocalDate currentDateMinus30Days = currentDate.minusDays(30);
        if(lastMonthlyFeeDate.isBefore(currentDateMinus30Days)) {
            Money newBalance = new Money(super.getBalance().decreaseAmount(MONTHLY_MAINTENANCE_FEE));
            super.setBalance(newBalance);
            setLastMonthlyFeeDate(currentDate);
            checkMinimumBalance();
        }
    }

    @Override
    public String toString() {
        return "Checking{" +
                "TYPE='" + TYPE + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", MINIMUM_BALANCE=" + MINIMUM_BALANCE +
                ", MONTHLY_MAINTENANCE_FEE=" + MONTHLY_MAINTENANCE_FEE +
                ", lastMonthlyFeeDate=" + lastMonthlyFeeDate +
                "} " + super.toString();
    }
}
