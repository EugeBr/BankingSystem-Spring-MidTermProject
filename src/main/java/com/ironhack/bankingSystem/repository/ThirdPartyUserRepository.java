package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.ThirdPartyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThirdPartyUserRepository extends JpaRepository<ThirdPartyUser, String> {

}
