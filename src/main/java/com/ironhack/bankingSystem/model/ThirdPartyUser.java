package com.ironhack.bankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyUser {

    @Id
    @NotEmpty
    private String hashedKey;
    @NotEmpty
    private String name;

}
