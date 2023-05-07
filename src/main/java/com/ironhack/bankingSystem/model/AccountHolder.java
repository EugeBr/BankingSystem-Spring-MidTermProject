package com.ironhack.bankingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "primary_address_id")
    private Address primaryAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mailing_address_id")
    private String mailingAddress;      //!optional

    public AccountHolder(String name, LocalDate dateOfBirth, Address primaryAddress) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }
}
