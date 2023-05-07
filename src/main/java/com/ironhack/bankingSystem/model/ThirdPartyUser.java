package com.ironhack.bankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyUser {

    @Id
    private String hashedKey;
    private String name;

}
