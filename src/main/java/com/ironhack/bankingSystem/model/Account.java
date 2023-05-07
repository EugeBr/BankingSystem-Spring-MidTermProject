package com.ironhack.bankingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal balance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;     //! optional
    private Double penaltyFee;
    private LocalDate creationDate = LocalDate.now();

    public Account(BigDecimal balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
    }

    public Account(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }
}
