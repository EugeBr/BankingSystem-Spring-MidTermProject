package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Integer> {

    @Query(value = "SELECT * FROM checking c JOIN account ac ON  ac.id = c.id JOIN account_holder a ON ac.primary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<Checking> findAllByPrimaryOwnerIdParam(@Param("idParam") Integer id);

    @Query(value = "SELECT * FROM checking c JOIN account ac ON  ac.id = c.id JOIN account_holder a ON ac.secondary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<Checking> findAllBySecondaryOwnerIdParam(@Param("idParam") Integer id);

    Optional<Checking> findBySecretKey(String secretKey);
}