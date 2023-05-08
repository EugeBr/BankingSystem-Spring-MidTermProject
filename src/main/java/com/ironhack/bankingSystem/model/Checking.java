package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checking extends Account{
    @NotEmpty
    private String secretKey;
    private final BigDecimal MINIMUM_BALANCE = new BigDecimal(250);
    private final BigDecimal MONTHLY_MAINTENANCE_FEE = new BigDecimal(12.0);
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private Status status;

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

}
