package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentChecking extends Account{
    @NotEmpty
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking(Money balance, AccountHolder primaryOwner, Admin createdBy, String secretKey, Status status) {
        super(balance, primaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner, createdBy);
        this.secretKey = secretKey;
        this.status = status;
    }
}
