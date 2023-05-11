package com.ironhack.bankingSystem.repository;

import com.ironhack.bankingSystem.classes.Money;
import com.ironhack.bankingSystem.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ironhack.bankingSystem.model.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentCheckingRepositoryTest {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    public void setUp() {
        Address address1 = new Address("Calle Falsa", "123");
        LocalDate date1 = LocalDate.of(1981, 7, 7);
        AccountHolder accountHolder1 = new AccountHolder("Lisa J. Dudley", "lisa", "1234", date1, address1);
        Admin admin = new Admin("Michael G. Pearson", "michael", "1234");

        BigDecimal amount = new BigDecimal(3000);
        Money balance = new Money(amount);
        StudentChecking studentChecking = new StudentChecking(balance, accountHolder1, admin, ACTIVE, "YUT655D");
        studentCheckingRepository.save(studentChecking);
    }

    @AfterEach
    public void tearDown() {
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    public void findAll_studentChecking_studentCheckingList() {
        List<StudentChecking> studentCheckingList = studentCheckingRepository.findAll();
        System.out.println(studentCheckingList);
        assertEquals(1, studentCheckingList.size());
    }

    @Test
    public void findById_validId_correctStudentChecking() {
        Optional<StudentChecking> studentCheckingOptional = studentCheckingRepository.findById(1);
        assertTrue(studentCheckingOptional.isPresent());
        System.out.println(studentCheckingOptional.get());
        assertEquals("Lisa J. Dudley", studentCheckingOptional.get().getPrimaryOwner().getName());
    }

    @Test
    public void findById_invalidId_studentCheckingNotPresent() {
        Optional<StudentChecking> studentCheckingOptional = studentCheckingRepository.findById(999);
        assertTrue(studentCheckingOptional.isEmpty());
    }

}