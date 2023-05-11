package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentChecking extends Account{
    private final String TYPE = "STUDENT CHECKING ACCOUNT";
    @NotEmpty
    private String secretKey;

    public StudentChecking(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status, String secretKey) {
        super(balance, primaryOwner, createdBy, status);
        this.secretKey = secretKey;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, createdBy, status);
        this.secretKey = secretKey;
    }

    // constructor that takes a Checking account and transforms it into a StudentChecking account
    public StudentChecking(Checking checking) {
        super(checking.getBalance(), checking.getPrimaryOwner(), checking.getSecondaryOwner(), checking.getCreatedBy(), checking.getStatus());
        this.secretKey = checking.getSecretKey();
    }

    @Override
    public String toString() {
        return "StudentChecking{" +
                "TYPE='" + TYPE + '\'' +
                ", secretKey='" + secretKey + '\'' +
                "} " + super.toString();
    }
}
