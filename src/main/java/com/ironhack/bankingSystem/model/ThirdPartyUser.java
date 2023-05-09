package com.ironhack.bankingSystem.model;

import jakarta.persistence.*;
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin addedBy;

}
