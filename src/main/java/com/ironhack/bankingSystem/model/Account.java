package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private Money balance;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_owner_id", nullable = false)
    private AccountHolder primaryOwner;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;
    private final BigDecimal PENALTY_FEE = new BigDecimal(40);
    @PastOrPresent
    private LocalDate creationDate = LocalDate.now();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin createdBy;

    public Account(Money balance, AccountHolder primaryOwner, Admin createdBy) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.createdBy = createdBy;
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.createdBy = createdBy;
    }

    public void applyPenaltyFee() {
        Money newBalance = new Money(getBalance().decreaseAmount(PENALTY_FEE));
        setBalance(newBalance);
    };

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", primaryOwner=" + primaryOwner +
                ", secondaryOwner=" + secondaryOwner +
                ", creationDate=" + creationDate +
                ", createdBy=" + createdBy.getName() +
                '}';
    }

}
