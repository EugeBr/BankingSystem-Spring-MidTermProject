package com.ironhack.bankingSystem.model;

import com.ironhack.bankingSystem.model.security.Role;
import com.ironhack.bankingSystem.model.security.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Admin extends User {

    public Admin(Long id, String name, String username, String password, Collection<Role> roles) {
        super(id, name, username, password, roles);
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
}
