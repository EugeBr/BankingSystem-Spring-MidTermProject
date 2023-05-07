package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Savings extends Account{
    private String secretKey;
    @Value("1000") //! not sure is correct
    @DecimalMin("100")
    private BigDecimal minimumBalance = new BigDecimal(1000);      //? default and min value
    @Enumerated(EnumType.STRING)
    private Status status;
    @Value("0.0025")
    @DecimalMax("0.5")                               //? max value
    private Double interestRate = 0.0025;           //? default?

    public Savings(BigDecimal balance, AccountHolder primaryOwner, String secretKey, Status status) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
    }

    public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.status = status;
    }
}
