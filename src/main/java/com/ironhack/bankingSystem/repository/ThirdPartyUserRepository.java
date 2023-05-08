package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.ThirdPartyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyUserRepository extends JpaRepository<ThirdPartyUser, String> {

}
