package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.CreditCard;
import com.ironhack.bankingSystem.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    @Query(value = "SELECT * FROM credit_card c JOIN account ac ON  ac.id = c.id JOIN account_holder a ON ac.primary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<CreditCard> findAllByPrimaryOwnerIdParam(@Param("idParam") Integer id);

    @Query(value = "SELECT * FROM credit_card c JOIN account ac ON  ac.id = c.id JOIN account_holder a ON ac.secondary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<CreditCard> findAllBySecondaryOwnerIdParam(@Param("idParam") Integer id);

}
