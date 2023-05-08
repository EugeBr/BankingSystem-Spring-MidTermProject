package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Integer> {

}
