package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.enums.Status;
import jakarta.persistence.*;
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
    @NotNull(message = "Balance can't be null")
    private Money balance;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "primary_owner_id", nullable = false)
    private AccountHolder primaryOwner;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency"))
    })
    private final Money PENALTY_FEE = new Money(new BigDecimal("40.00"));
    @PastOrPresent
    private LocalDate creationDate = LocalDate.now();
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin createdBy;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status can't be null")
    private Status status;

    public Account(Money balance, AccountHolder primaryOwner, Admin createdBy, Status status) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.createdBy = createdBy;
        this.status = status;
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Admin createdBy, Status status) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.createdBy = createdBy;
        this.status = status;
    }

    public void applyPenaltyFee() {
        Money newBalance = new Money(getBalance().decreaseAmount(PENALTY_FEE));
        setBalance(newBalance);
    };

    public void checkMinimumBalance() {
        System.out.println("Nothing to do here");
    };

    public void checkMonthlyFee() {
        System.out.println("Nothing to do here");
    };

    public void checkInterest() {
        System.out.println("Nothing to do here");
    };

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", primaryOwner=" + primaryOwner +
                ", secondaryOwner=" + secondaryOwner +
                ", PENALTY_FEE=" + PENALTY_FEE +
                ", creationDate=" + creationDate +
                ", createdBy=" + createdBy.getName() +
                ", status=" + status +
                '}';
    }
}
