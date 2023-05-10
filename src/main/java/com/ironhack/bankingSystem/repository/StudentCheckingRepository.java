package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.Checking;
import com.ironhack.bankingSystem.model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Integer> {

    @Query(value = "SELECT * FROM student_checking s JOIN account ac ON  ac.id = s.id JOIN account_holder a ON ac.primary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<StudentChecking> findAllByPrimaryOwnerIdParam(@Param("idParam") Integer id);

    @Query(value = "SELECT * FROM student_checking s JOIN account ac ON  ac.id = s.id JOIN account_holder a ON ac.secondary_owner_id = a.id WHERE a.id = :idParam",
            nativeQuery = true)
    List<StudentChecking> findAllBySecondaryOwnerIdParam(@Param("idParam") Integer id);
}
