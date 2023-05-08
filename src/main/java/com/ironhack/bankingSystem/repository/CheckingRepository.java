package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {

}
