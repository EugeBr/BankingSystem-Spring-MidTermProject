package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.model.security.Role;
import com.ironhack.bankingSystem.model.security.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder extends User {
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "primary_address_id", nullable = false)
    private Address primaryAddress;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "mailing_address_id")
    private Address mailingAddress;

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address primaryAddress) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }
}
