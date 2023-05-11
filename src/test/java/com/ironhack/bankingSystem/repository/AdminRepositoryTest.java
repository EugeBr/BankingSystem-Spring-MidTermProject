package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.model.AccountHolder;
import com.ironhack.bankingSystem.model.Address;
import com.ironhack.bankingSystem.model.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        Admin admin1 = new Admin("Lisa J. Dudley", "lisa", "1234");
        adminRepository.save(admin1);
        Admin admin2 = new Admin("Michael G. Pearson", "lisa", "1234");
        adminRepository.save(admin2);
    }

    @AfterEach
    public void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    public void findAll_admins_adminsList() {
        List<Admin> adminList = adminRepository.findAll();
        System.out.println(adminList);
        assertEquals(2, adminList.size());
    }

    @Test
    public void findById_validId_correctAdmin() {
        Optional<Admin> adminOptional = adminRepository.findById(1);
        assertTrue(adminOptional.isPresent());
        System.out.println(adminOptional.get());
        assertEquals("Lisa J. Dudley", adminOptional.get().getName());
    }

    @Test
    public void findById_invalidId_adminNotPresent() {
        Optional<Admin> adminOptional = adminRepository.findById(999);
        assertTrue(adminOptional.isEmpty());
    }

}