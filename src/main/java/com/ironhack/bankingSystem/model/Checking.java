package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private final BigDecimal MINIMUM_BALANCE = new BigDecimal("250.00");
    private final BigDecimal MONTHLY_MAINTENANCE_FEE = new BigDecimal("12.0");
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status can't be null")
    private Status status;
    @PastOrPresent(message = "Date can't be in the future")
    private LocalDate lastMonthlyFeeDate = LocalDate.now();

    public Checking(Money balance, AccountHolder primaryOwner, Admin createdBy, String secretKey, Status status) {
        super(balance, primaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
    }

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
    }

    //check if balance is lower that minimum balance
    @Override
    public void checkMinimumBalance() {
        if(getMINIMUM_BALANCE().compareTo(getBalance().getAmount()) == 1) {
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
                "secretKey='" + secretKey + '\'' +
                ", status=" + status +
                ", lastMonthlyFeeDate=" + lastMonthlyFeeDate +
                "} " + super.toString();
    }
}
