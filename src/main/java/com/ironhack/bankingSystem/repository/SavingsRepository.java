package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Integer> {

    @Query(value = "SELECT * FROM savings s JOIN account ac ON  ac.id = s.id JOIN account_holder a ON ac.primary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<Savings> findAllByPrimaryOwnerIdParam(@Param("idParam") Integer id);

    @Query(value = "SELECT * FROM savings s JOIN account ac ON  ac.id = s.id JOIN account_holder a ON ac.secondary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<Savings> findAllBySecondaryOwnerIdParam(@Param("idParam") Integer id);

    Optional<Savings> findBySecretKey(String secretKey);
}
