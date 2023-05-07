package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentChecking extends Account{
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking(BigDecimal balance, AccountHolder primaryOwner, String secretKey, Status status) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
    }

    public StudentChecking(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.status = status;
    }
}
